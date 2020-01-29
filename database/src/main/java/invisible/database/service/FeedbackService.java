package invisible.database.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import invisible.database.models.objects.Feedback;
import invisible.database.repository.FeedbackRepository;

import java.util.List;
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
@Service
public class FeedbackService {

  private final String FEEDBACK_SEQUENCE = "feedback_sequence";

  private final FeedbackRepository feedbackRepository;
  private SequenceGeneratorService sequenceGeneratorService;

  public FeedbackService(FeedbackRepository feedbackRepository, SequenceGeneratorService sequenceGeneratorService) {
    this.feedbackRepository = feedbackRepository;
    this.sequenceGeneratorService = sequenceGeneratorService;
  }

  public ResponseEntity<Long> postFeedback(Feedback feedback){
    feedback.setId(sequenceGeneratorService.generateId(FEEDBACK_SEQUENCE));
    Optional<Feedback> feedbackFromDB = feedbackRepository.findById(feedback.getId());
    return new ResponseEntity<>(feedbackRepository.save(feedback).getId(),HttpStatus.OK);
  }

  public ResponseEntity<Feedback> getFeedback(Long feedbackID) {
    Optional<Feedback> byId = feedbackRepository.findById(feedbackID);
    return byId.map(feedback -> new ResponseEntity<>(feedback, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  public ResponseEntity<List<Feedback>> getAllOpenFeedback() {
    List<Feedback> feedbacks = feedbackRepository.findFeedbacksBySolvedIsFalse();
    return new ResponseEntity<>(feedbacks, HttpStatus.OK);
  }
}
