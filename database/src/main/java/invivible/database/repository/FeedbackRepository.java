package invivible.database.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import invivible.database.models.objects.Feedback;
import invivible.database.models.user.User;

import java.util.List;

/**
 * Project:        In_Visible
 * <p>
 * Author:         Moritz Thomas
 * <p>
 * Creation date:  11.12.2019
 * <p>
 * <p/>
 */
@Service
public interface FeedbackRepository extends MongoRepository<Feedback, Long> {

  List<Feedback> findFeedbacksBySolvedIsFalse();
}
