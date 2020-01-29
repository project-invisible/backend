package invisible.database.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import invisible.database.models.objects.Feedback;
import invisible.database.service.FeedbackService;

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
@RestController
@RequestMapping("/feedback")
public class FeedbackController {

  private final FeedbackService feedbackService;

  public FeedbackController(FeedbackService feedbackService) {
    this.feedbackService = feedbackService;
  }

  @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Long> postFeedback(@RequestBody Feedback feedback){
    return feedbackService.postFeedback(feedback);
  }

  @GetMapping(value = "/{feedbackID}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Feedback> getFeedback(@PathVariable Long feedbackID) {
    return feedbackService.getFeedback(feedbackID);
  }

  @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Feedback>> getAllOpenFeedback() {
    return feedbackService.getAllOpenFeedback();
  }
}

