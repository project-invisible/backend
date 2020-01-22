package invivible.database.auth.tokens;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import java.security.Key;

@Service
public class JWTokenHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(JWAuthTokenFactory.class);


    public static final String NAME_OF_TYPE_CLAIM = "tType";
    public static final String TYPE_OF_TOKEN = "JWToken";


    @Autowired
    private KeyService keyService;


    // default ttl is 30min
    @Value("#{${jwt.timeToLiveMinutes:30} * 60L * 1000L}")
    protected long defaultTtlMillis;

    protected static final String ISSUER = "projektpool.adesso.de";


    protected JWToken readJWTokenFromString(String tokenString, Key key) {
        JWAuthToken result = new JWAuthToken();

        try {
            Jws<Claims> jws = Jwts.parser().setSigningKey(key).parseClaimsJws(tokenString);
            Claims claims = jws.getBody();

            result.id = claims.getId();
            result.subject = claims.getSubject();
            result.audience = claims.getAudience();
            result.issuer = claims.getIssuer();
            result.tType = claims.get(NAME_OF_TYPE_CLAIM, String.class);
            if (claims.getIssuedAt() != null) result.createdMillis = claims.getIssuedAt().getTime();
            if (claims.getExpiration() != null) result.expiredMillis = claims.getExpiration().getTime();
            result.setIsValid(true);
        } catch (Exception e) {
            result.setIsValid(false);
        }
        return result;
    }

    public JWToken readJWTokenFromString(String tokenString) {
        return readJWTokenFromString(tokenString, keyService.getSigningKey());
    }

    public String getTypeOfToken(String token)
    {
        Claims claims = Jwts.parser().setSigningKey(keyService.getSigningKey()).parseClaimsJws(token).getBody();
        return claims.get(NAME_OF_TYPE_CLAIM, String.class);
    }


}
