package invivible.tokenApp.tokens;

import java.util.Calendar;

public class JWToken {

    public String id;

    public String issuer;

    public long createdMillis;

    public long expiredMillis;

    protected boolean isValid = false;

    public String subject;

    public String audience;

    //which kind of token it is
    public String tType;

    public boolean isExpired() {
        Calendar now = Calendar.getInstance();

        Calendar expiration = Calendar.getInstance();
        expiration.setTimeInMillis(expiredMillis);

        return now.after(expiration);
    }


    public boolean isValid() {
        return isValid;
    }

    public void setIsValid(boolean isValid) {
        this.isValid = isValid && !isExpired();
    }

    public String getTokenType() {
        return tType;
    }
}
