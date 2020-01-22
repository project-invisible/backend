package invivible.database.auth.tokens;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

@Service
public class KeyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(KeyService.class);

    @Value("${jwt.signing.keyfile.path}")
//    @Value("${jwt.signing.keyfile.path:signing.priv.base64.key}")
    private String signingKeyFilePath;

    @Getter
    private final SignatureAlgorithm JJWT_SIGNATURE_ALGO = SignatureAlgorithm.HS512;

    @Getter
    private final String JCA_NAME_HMAC_SHA_512 = "HmacSHA512"; // the JCA name for the HS512 algorithm

    @Getter
    private SecretKeySpec signingKey;

    @PostConstruct
    public void postConstruct(){
        // key in file must have been/ can be created with KeyGenerator.generateRandomHS256KeyBase64
        // we get the secret signing key from a file not in src control in our production systems. (might even get from a crypted Java keystore?)
        byte[] signingKeyBytes;
        try {
            LOGGER.info("try loading JWT signing key from file " + signingKeyFilePath + " from filesystem");
            signingKeyBytes = getSigningKeyBytesFromResource( new FileSystemResource( signingKeyFilePath ) );
        } catch (Exception e) {
            LOGGER.warn("try loading JWT signing key from file " + signingKeyFilePath + " from classpath");
            // try classPathResource as a fallback and default case:
            // if that throws, than just let fall through
            signingKeyBytes = getSigningKeyBytesFromResource( new ClassPathResource( signingKeyFilePath ) );
        }

        signingKey = new SecretKeySpec(signingKeyBytes, JCA_NAME_HMAC_SHA_512); // need the JCA-name here
    }

    private byte[] getSigningKeyBytesFromResource(Resource base64SigningKeyFile) {
        final String signingKeyBase64;

        // try-with-resources: all wrapped file system resources will be auto-closed
        try (BufferedReader streamReader =
                     new BufferedReader( new InputStreamReader(base64SigningKeyFile.getInputStream(), StandardCharsets.US_ASCII) )) {
            // read lines, filter out comments, and concatenate them
            Optional<String> maybeKey = streamReader.lines()
                    .filter( line -> !line.startsWith("--") )
                    .reduce( String::concat );
            signingKeyBase64 = maybeKey.orElseThrow(
                    () -> new IllegalStateException("could not read signing key from key file, cannot sign any JWTs, exiting VM"));
        } catch (IOException e) {
            throw new IllegalStateException("could not read signing key from key file, cannot sign any JWTs, exiting VM");
        }


        return Base64.getDecoder().decode( signingKeyBase64 );
    }
}
