package invisible.database.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import invisible.database.models.user.User;

import java.util.Optional;

/**
 * Project:        In_Visible
 * <p>
 * Author:         Moritz Thomas
 * <p>
 * Creation date:  11.12.2019
 * <p>
 * <p/>
 */
@Repository
public interface UserRepository extends MongoRepository<User, Long> {

  Optional<User> findByEmail(String email);
}
