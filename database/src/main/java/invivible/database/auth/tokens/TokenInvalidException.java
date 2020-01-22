package invivible.database.auth.tokens;

/**
 * @author <a href="mailto:furtner@adesso.de">Andreas Furtner</a>
 * @since 25.06.2018
 */
public class TokenInvalidException extends IllegalArgumentException {
    public TokenInvalidException() {
    }

    public TokenInvalidException(String s) {
        super(s);
    }

    public TokenInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenInvalidException(Throwable cause) {
        super(cause);
    }
}
