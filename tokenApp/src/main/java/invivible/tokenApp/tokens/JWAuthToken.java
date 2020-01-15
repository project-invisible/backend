/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invivible.tokenApp.tokens;


/**
 * @author odzhara-ongom
 */
public class JWAuthToken extends JWToken{







  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getIssuer() {
    return issuer;
  }

  public void setIssuer(String issuer) {
    this.issuer = issuer;
  }

  public String getAudience() {
    return audience;
  }

  public void setAudience(String audience) {
    this.audience = audience;
  }

  public long getCreatedMillis() {
    return createdMillis;
  }

  public void setCreatedMillis(long createdMillis) {
    this.createdMillis = createdMillis;
  }

  public long getExpiredMillis() {
    return expiredMillis;
  }

  public void setExpiredMillis(long expiredMillis) {
    this.expiredMillis = expiredMillis;
  }



}
