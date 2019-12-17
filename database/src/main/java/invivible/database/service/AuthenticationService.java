package invivible.database.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import invivible.database.models.user.User;
import invivible.database.repository.UserRepository;

import java.util.Optional;

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

  private final UserRepository userRepository;

  public AuthenticationService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public ResponseEntity<String> registerUserInDB(User user) {
    Optional<User> byId = this.userRepository.findById(user.getId());
    return byId.map(value -> new ResponseEntity<>("User mit der Id: " + value.getId() + "already registered.", HttpStatus.BAD_REQUEST))
        .orElseGet(() -> new ResponseEntity<>("User mit der Id: " + this.userRepository.save(user).getId() + " registered.", HttpStatus.ACCEPTED));
  }

  public ResponseEntity<String> authenticateUser(User user) {
    Optional<User> byId = this.userRepository.findById(user.getId());
    if(byId.isPresent()) {
//      compare passwords --> authenticate
      if(comparePasswords(user.getPassword(), byId.get().getPassword())) {
        return new ResponseEntity<>("User with Id: " + byId.get().getId() + "authenticated.", HttpStatus.ACCEPTED);
      }
//      mismatch
      else {
        return new ResponseEntity<>("User with Id: " + byId.get().getId() + "could not be authenticated. Passwords mismatch.", HttpStatus.UNAUTHORIZED);
      }
    }
    return null;
  }

  private boolean comparePasswords(String passwordDto, String passwordDB) {
    return true;
  }
}
