package invivible.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

/**
 * Project:        In_Visible
 * <p>
 * Author:         Moritz Thomas
 * <p>
 * Creation date:  17.12.2019
 * <p>
 * <p/>
 */
@Configuration
public class ApplicationConfig {

  /**
   * Use RestTemplate loadbalanced to call REST-Endpoints in other modules
   * @return
   */
  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

}
