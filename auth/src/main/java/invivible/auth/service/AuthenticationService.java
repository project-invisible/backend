package invivible.auth.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import invivible.auth.models.UserDto;

/**
 * Project:        ProjektPool
 * <p>
 * Author:         Moritz Thomas
 * <p>
 * Creation date:  17.12.2019
 * <p>
 * <p/>
 */
@Service
public class AuthenticationService {

  private RestTemplate restTemplate;


  public AuthenticationService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public UserDto registerNewUser(UserDto user) {
    ResponseEntity<String> stringResponseEntity =
        restTemplate.postForEntity("http://projectDB/authenticate/register",
            new HttpEntity<>(user), String.class);
    return new UserDto();
  }

  public UserDto authenticateUser(UserDto user) {
    ResponseEntity<String> stringResponseEntity =
        restTemplate.postForEntity("http://projectDB/authenticate",
            new HttpEntity<>(user), String.class);
    return new UserDto();
  }

}
