package invisible.database.auth.tokens;

/**
 * @author <a href="mailto:furtner@adesso.de">Andreas Furtner</a>
 * @since 29.08.2018
 */
public class TokenUtil {

    private static final int HEADER_EXC_LEN = 12;
    private static final int EXCERPTS_LEN = 16;

    public String uglifyForLogging(final String tokenString) {

        if (tokenString != null && tokenString.length() >= HEADER_EXC_LEN) {
            // on regular tokens this will just be the base64-coded header, not very distinctive:
            String tokenForLog = tokenString.substring(0, HEADER_EXC_LEN) + "_SNIP_";

            int pailoadStart = tokenString.indexOf('.');
            int sigStart = tokenString.lastIndexOf('.');

            if (pailoadStart > 0 && sigStart > 0 && sigStart > pailoadStart + EXCERPTS_LEN + 1) {
                // log a little bit of payload but don't log signature/digest part at all
                tokenForLog += tokenString.substring(pailoadStart, pailoadStart + EXCERPTS_LEN + 1) + "..._SNIP_...";

//            } else {
                // don't bother extra logging strange tokens which do not comply to above condition
            }
            return tokenForLog;
        } else {
            // this is mostly for null tokens but I don't bother uglifying "short" tokens, too, just return them.
            // all valid JSON web tokens will have length > 5, no need to uglify invalid tokenStrings ;-)
            return tokenString;
        }
    }
}
