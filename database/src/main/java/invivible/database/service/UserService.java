package invivible.database.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import invivible.database.auth.tokens.JWAuthToken;
import invivible.database.auth.tokens.JWAuthTokenFactory;
import invivible.database.models.enums.Role;
import invivible.database.models.user.User;
import invivible.database.repository.UserRepository;

import java.util.Optional;

/**
 * Project:        In_Visible
 * <p>
 * Author:         Moritz Thomas
 * <p>
 * Creation date:  23.01.2020
 * <p>
 * <p/>
 */
@Service
public class UserService {

  private final UserRepository userRepository;
  private final JWAuthTokenFactory tokenFactory;

  public UserService(UserRepository userRepository, JWAuthTokenFactory tokenFactory) {
    this.userRepository = userRepository;
    this.tokenFactory = tokenFactory;
  }

  public ResponseEntity<User> getUser(Long userId) {
    Optional<User> byId = userRepository.findById(userId);
    return byId.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  public ResponseEntity<User> postUser(User user, String token) {
    JWAuthToken jwAuthToken = tokenFactory.readJWTokenFromString(token);
    if(!jwAuthToken.getAudience().equals(Role.ADMIN.toString())) {
      return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
  }
}
