package invivible.database.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import invivible.database.models.objects.Feedback;
import invivible.database.service.FeedbackService;

/**
 * Project:        ProjektPool
 * <p>
 * Author:         Moritz Thomas
 * <p>
 * Creation date:  11.12.2019
 * <p>
 * <p/>
 */
@RestController
@RequestMapping("/feedback")
public class FeedbackController {

  private final FeedbackService feedbackService;

  public FeedbackController(FeedbackService feedbackService) {
    this.feedbackService = feedbackService;
  }

  @PostMapping()
  public ResponseEntity<String> postFeedback(@RequestBody Feedback feedback){
    long id = feedbackService.postFeedback(feedback);
    return new ResponseEntity<>("Succesfully posted Feedback " + id, HttpStatus.OK);
  }
}

