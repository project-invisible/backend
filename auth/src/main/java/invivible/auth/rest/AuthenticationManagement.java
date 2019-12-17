package invivible.auth.rest;

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

  private final AuthenticationService authenticationService;

  public AuthenticationManagement(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @PostMapping("/register")
  public UserDto registerUserAccount(@RequestBody UserDto userDto) {

    return this.authenticationService.registerNewUser(userDto);
  }

  @PostMapping()
  public UserDto authenticateUser(@RequestBody UserDto user) {

    return this.authenticationService.authenticateUser(user);
  }
}
