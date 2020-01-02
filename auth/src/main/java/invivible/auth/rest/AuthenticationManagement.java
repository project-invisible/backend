package invivible.auth.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import invivible.auth.models.UserDto;
import invivible.auth.service.AuthenticationService;

/**
 * Project:        ProjektPool
 * <p>
 * Author:         Moritz Thomas
 * <p>
 * Creation date:  17.12.2019
 * <p>
 * <p/>
 */
@RestController
@RequestMapping("/authenticate")
public class AuthenticationManagement {

  private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationManagement.class);

  private final AuthenticationService authenticationService;

  public AuthenticationManagement(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> registerUserAccount(@RequestBody UserDto userDto) {
    LOGGER.info("Authentication request from: " + userDto.getEmail());
    return this.authenticationService.registerNewUser(userDto);
  }

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> authenticateUser(@RequestBody UserDto user) {

    return this.authenticationService.authenticateUser(user);
  }
}
