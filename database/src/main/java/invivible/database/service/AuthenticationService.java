package invivible.database.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import invivible.database.models.enums.Role;
import invivible.database.models.enums.Status;
import invivible.database.models.helper.Contact;
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

  public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, SequenceGeneratorService sequenceGeneratorService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.sequenceGeneratorService = sequenceGeneratorService;
  }

  public ResponseEntity<String> registerUserInDB(UserDto user) {
    Optional<User> byId = this.userRepository.findByEmail(user.getEmail());
    if(!byId.isPresent()){
      User newUser =
          new User(sequenceGeneratorService.generateId(user_sequence),
              user.getEmail(),
              user.getUsername(),
              false,
              Role.USER,
              new Date(),
              passwordEncoder.encode(user.getPassword()),
              new Contact(null, null, 0),
              Status.ACTIVE,
              null);
      //    set user anonymous if no username chosen
      if(user.getUsername() == null || user.getUsername().equals("")){
        newUser.setAnonymous(true);
      }
      return new ResponseEntity<>(this.userRepository.save(newUser).getId().toString(), HttpStatus.OK);
    } else {
      LOGGER.warn("User with email: " + user.getEmail() + " already registered.");
      return new ResponseEntity<>("Email already registered.", HttpStatus.BAD_REQUEST);
    }
  }

  public ResponseEntity<String> authenticateUser(UserDto user) {
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
    return passwordEncoder.matches(passwordDto, passwordDB);
  }
}
