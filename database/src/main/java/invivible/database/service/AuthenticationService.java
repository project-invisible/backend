package invivible.database.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import invivible.database.auth.tokens.JWAuthTokenFactory;
import invivible.database.models.enums.Role;
import invivible.database.models.enums.Status;
import invivible.database.models.helper.Contact;
import invivible.database.models.user.AuthenticationResponse;
import invivible.database.models.user.User;
import invivible.database.models.user.UserDto;
import invivible.database.repository.UserRepository;

import java.util.Date;
import java.util.Optional;

/**
 * Project:        In_Visible
 * <p>
 * Author:         Moritz Thomas
 * <p>
 * Creation date:  17.12.2019
 * <p>
 * <p/>
 */
@Service
public class AuthenticationService {

  private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);

  private final String user_sequence = "user_sequence";

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final SequenceGeneratorService sequenceGeneratorService;
  private final JWAuthTokenFactory tokenFactory;

  public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, SequenceGeneratorService sequenceGeneratorService, JWAuthTokenFactory tokenFactory) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.sequenceGeneratorService = sequenceGeneratorService;
    this.tokenFactory = tokenFactory;
  }

  public ResponseEntity<AuthenticationResponse> registerUserInDB(UserDto user) {
    Optional<User> byId = this.userRepository.findByEmail(user.getEmail());
    AuthenticationResponse response = new AuthenticationResponse();
    if(!byId.isPresent()){
      User newUser =
          new User(sequenceGeneratorService.generateId(user_sequence),
              user.getEmail(),
              null,
              false,
              Role.USER,
              new Date(),
              passwordEncoder.encode(user.getPassword()),
              new Contact(user.getEmail(), null, 0),
              Status.ACTIVE,
              null);
      //    set user anonymous if no username chosen
      if(user.getUsername() == null || user.getUsername().equals("")){
        newUser.setAnonymous(true);
      } else {
        newUser.setUsername(user.getUsername());
      }
      response.setExists(true);
      response.setEmail(user.getEmail());
      response.setGroup(Role.USER);
      response.setId(newUser.getId());
      response.setToken(tokenFactory.getToken(newUser.getId().toString(), newUser.getEmail(), newUser.getRole().toString()));
      return new ResponseEntity<>(response, HttpStatus.OK);
    } else {
      LOGGER.warn("User with email: " + user.getEmail() + " already registered.");
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
  }

  public ResponseEntity<AuthenticationResponse> authenticateUser(UserDto user) {
    Optional<User> byMail = this.userRepository.findByEmail(user.getEmail());
    AuthenticationResponse response = new AuthenticationResponse();
    if(byMail.isPresent()) {
      User userDB = byMail.get();
//      compare passwords --> authenticate
      if(comparePasswords(user.getPassword(), byMail.get().getPassword())) {
        response.setExists(true);
        response.setId(userDB.getId());
        response.setGroup(userDB.getRole());
        response.setEmail(userDB.getEmail());
        response.setToken(tokenFactory.getToken(userDB.getId().toString(), userDB.getEmail(), userDB.getRole().toString()));
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
      }
//      mismatch
      else {
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
      }
    }
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

  private boolean comparePasswords(String passwordDto, String passwordDB) {
    return passwordEncoder.matches(passwordDto, passwordDB);
  }
}
