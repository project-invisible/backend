package invisible.database.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import invisible.database.models.user.User;
import invisible.database.service.UserService;

/**
 * Project:        In_Visible
 * <p>
 * Author:         Moritz Thomas
 * <p>
 * Creation date:  23.01.2020
 * <p>
 * <p/>
 */
@RestController
@RequestMapping("/db/user")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<User> getUser(@PathVariable Long userId) {
    return userService.getUser(userId);
  }

  @PostMapping()
  public ResponseEntity<User> postUser(@RequestBody User user, @RequestHeader(name = "Authorization") String token) {
    return userService.postUser(user, token);
  }
}
