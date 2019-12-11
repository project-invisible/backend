package invivible.database.service;

import org.springframework.stereotype.Service;

import invivible.database.models.objects.Feedback;
import invivible.database.repository.FeedbackRepository;

import java.util.Optional;

/**
 * Project:        ProjektPool
 * <p>
 * Author:         Moritz Thomas
 * <p>
 * Creation date:  11.12.2019
 * <p>
 * <p/>
 */
@Service
public class FeedbackService {

  private final FeedbackRepository feedbackRepository;

  public FeedbackService(FeedbackRepository feedbackRepository) {
    this.feedbackRepository = feedbackRepository;
  }

  public long postFeedback(Feedback feedback){
    Optional<Feedback> feedbackFromDB = feedbackRepository.findById(feedback.getId());
    return feedbackRepository.save(feedback).getId();
  }
}
