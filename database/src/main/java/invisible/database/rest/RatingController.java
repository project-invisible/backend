package invisible.database.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import invisible.database.models.objects.PreviewRatingDTO;
import invisible.database.models.objects.Rating;
import invisible.database.service.RatingService;

import java.util.List;

/**
 * Project:        In_Visible
 * <p>
 * Author:         Moritz Thomas
 * <p>
 * Creation date:  27.12.2019
 * <p>
 * <p/>
 */
@RestController
@RequestMapping("/db/rating")
public class RatingController {

  private final RatingService ratingService;

  public RatingController(RatingService ratingService) {
    this.ratingService = ratingService;
  }

  @GetMapping( value = "/poi/{poiId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Rating>> getAllRatingsForPoi(@PathVariable Long poiId) {
    List<Rating> allRatingsForPoi = ratingService.getAllRatingsForPoi(poiId);
    if( allRatingsForPoi != null && allRatingsForPoi.size() < 1) {
      return new ResponseEntity<>(allRatingsForPoi, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

//  @GetMapping("/{entryID}")
//  public ResponseEntity<List<Rating>> getAllRatingsForEntry(@PathVariable Long entryID) {
//    List<Rating> allRatingsForEntry = ratingService.getAllRatingsForEntry(entryID);
//    if( allRatingsForEntry != null) {
//      return new ResponseEntity<>(allRatingsForEntry, HttpStatus.OK);
//    } else {
//      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//  }

  @PostMapping(  value = "", produces = MediaType.APPLICATION_JSON_VALUE )
  public ResponseEntity<String> postRating(@RequestBody Rating rating) {
    Long ratingId = ratingService.postRating(rating);
    if(ratingId != null) {
      return new ResponseEntity<>("Rating saved with Id: " + ratingId, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping(value = "/{ratingId}", produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<Rating> getRating(@PathVariable Long ratingId) {
   return ratingService.getRating(ratingId);
   }

   @GetMapping( value = "/newest/{poiID}", produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<List<Rating>> getNewestRatingsForPoi(@PathVariable Long poiID) {
   List<Rating> newestRatingForPoi = ratingService.getNewestRatingForPoi(poiID);
   if(newestRatingForPoi != null) {
   return new ResponseEntity<>(newestRatingForPoi, HttpStatus.OK);
   } else {
   return new ResponseEntity<>(HttpStatus.NOT_FOUND);
   }
   }

   /**
   * Return the defined top 5 questions for Poi to didplay at the detail view
   * Currently the top 5 are hardcoded
   * Could change that to datamodel field or config parameter/ variable
   * @param poiID
   * @return
   */
  @GetMapping(value = "/top/{poiID}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<PreviewRatingDTO>> getTopQuestionsForPoi(@PathVariable Long poiID) {
    return ratingService.getTopQuestionsForPoi(poiID);
  }

//  @GetMapping("/newest/{entryID}")
//  public ResponseEntity<List<Rating>> getNewestRatingsForEntry(@PathVariable Long entryID) {
//    List<Rating> newestRatingForEntry = ratingService.getNewestRatingForEntry(entryID);
//    if(newestRatingForEntry != null) {
//      return new ResponseEntity<>(newestRatingForEntry, HttpStatus.OK);
//    } else {
//      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//  }
}
