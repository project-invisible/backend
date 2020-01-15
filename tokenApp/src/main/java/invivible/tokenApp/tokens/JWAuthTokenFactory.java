package invivible.tokenApp.tokens;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author odzhara-ongom
 * @author <a mailto:"furtner@adeesso.de">Andreas Furtner</a>
 */
@Service
public class JWAuthTokenFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(JWAuthTokenFactory.class);
    public static final String TYPE_OF_TOKEN = "AuthToken";

    private static final String ISSUER = "projektpool.adesso.de";

    @Value("#{${jwt.timeToLiveMinutes:30} * 60L * 1000L}")
    protected long defaultTtlMillis;

    @Autowired
    private KeyService keyService;

    /**
     * get a base64-coded HmacSHA512 signed JWT string for the supplied claims (with a default timeToLive (expiry time))
     */
    public String getToken(String userId, String username, String role) {
        Key key = keyService.getSigningKey();
        return createJWT(userId, ISSUER, username, role, defaultTtlMillis, key, keyService.getJJWT_SIGNATURE_ALGO());
    }

    /**
     * same as above, but with a non-default timeToLive (expiry time)
     */
    public String getToken(String userId, String username, String role, long ttlMillis) {
        Key key = keyService.getSigningKey();
        return createJWT(userId, ISSUER, username, role, ttlMillis, key, keyService.getJJWT_SIGNATURE_ALGO());
    }



    public String renewToken(String tokenString) {
        return renewToken(tokenString, keyService.getSigningKey(), defaultTtlMillis, keyService.getJJWT_SIGNATURE_ALGO());
    }



    /**
     * @return a _new_ JSON web token based on the input token, with new timeToLive (expiry time)
     */
    private String createJWT(JWAuthToken token, long ttlMillis, Key key, SignatureAlgorithm signatureAlgorithm) {

        return createJWT(token.id, token.issuer, token.subject, token.audience, ttlMillis, key, signatureAlgorithm);
    }

    private String createJWT(String id,
                             String issuer,
                             String subject,
                             String audience,
                             long ttlMillis,
                             Key key,
                             SignatureAlgorithm signatureAlgorithm) {

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Map<String, Object> claims = new HashMap<>();
        claims.put(JWTokenHelper.NAME_OF_TYPE_CLAIM, TYPE_OF_TOKEN);
        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .setAudience(audience)
                .signWith(signatureAlgorithm, key)
                .addClaims(claims);

        //if it has been specified, let's add the expiration
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }



    private String renewToken(String tokenString, Key key, long timeToExtend, SignatureAlgorithm signatureAlgorithm) {

        JWAuthToken token = readJWTokenFromString(tokenString, key);
        if (token.isValid() && !token.isExpired()) {

            LOGGER.debug("renew token for user: "+token.getSubject());
            // create a new JWToken with the same details but new extended expiry time
            return createJWT(token, timeToExtend, key, signatureAlgorithm);
        } else {

            String msg = !token.isValid() ? "token string is not valid." : "token is expired." ;
            LOGGER.warn("cannot renew token: " + msg);
            throw new TokenInvalidException( msg );
        }
    }



    public JWAuthToken readJWTokenFromString(String tokenString) {
        return readJWTokenFromString(tokenString, keyService.getSigningKey());
    }

    protected JWAuthToken readJWTokenFromString(String tokenString, Key key) {
        JWAuthToken result = new JWAuthToken();

        try {
            Jws<Claims> jws = Jwts.parser().setSigningKey(key).parseClaimsJws(tokenString);
            Claims claims = jws.getBody();

            result.id = claims.getId();
            result.subject = claims.getSubject();
            result.audience = claims.getAudience();
            result.issuer = claims.getIssuer();
            result.tType = claims.get(JWTokenHelper.NAME_OF_TYPE_CLAIM, String.class);
            if (claims.getIssuedAt() != null) {
                result.createdMillis = claims.getIssuedAt().getTime();
            }
            if (claims.getExpiration() != null) {
                result.expiredMillis = claims.getExpiration().getTime();
            }
            result.setIsValid(true);
        } catch (Exception e) {

            LOGGER.warn("got exception when decoding token " + new TokenUtil().uglifyForLogging(tokenString), e);
            result.setIsValid(false);
        }
        return result;
    }


}
