package invivible.auth.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
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

  public ResponseEntity<String> registerNewUser(UserDto user) {
    try {
      return restTemplate.postForEntity("http://localhost:8182/authenticate/register",
          new HttpEntity<>(user), String.class);
    } catch (HttpClientErrorException e) {
      e.printStackTrace();
      return new ResponseEntity<>("Email already registered", HttpStatus.BAD_REQUEST);
    }
  }

  public ResponseEntity<String> authenticateUser(UserDto user) {
    return restTemplate.postForEntity("http://localhost:8182/authenticate",
        new HttpEntity<>(user), String.class);
  }

}
