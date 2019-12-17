package invivible.database.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import invivible.database.models.user.User;
import invivible.database.service.AuthenticationService;


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
public class AuthenticationController {


  private final AuthenticationService authenticationService;

  public AuthenticationController(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @PostMapping("/register")
  public ResponseEntity<String> registerUserInDB(@RequestBody User user) {
    return this.authenticationService.registerUserInDB(user);
  }

  @PostMapping("")
  public ResponseEntity<String> authenticateUserInDB(@RequestBody User user) {
    return this.authenticationService.authenticateUser(user);
  }
}
