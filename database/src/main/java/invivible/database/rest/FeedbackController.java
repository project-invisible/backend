package invivible.database.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import invivible.database.models.objects.Feedback;
import invivible.database.service.FeedbackService;

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

  @PostMapping()
  public ResponseEntity<String> postFeedback(@RequestBody Feedback feedback){
    long id = feedbackService.postFeedback(feedback);
    return new ResponseEntity<>("Succesfully posted Feedback " + id, HttpStatus.OK);
  }

  @GetMapping(value = "/{feedbackID}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Feedback> getFeedback(@PathVariable Long feedbackID) {
    Feedback feedback = feedbackService.getFeedback(feedbackID);
    if(feedback != null) {
      return new ResponseEntity<>(feedback, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}

