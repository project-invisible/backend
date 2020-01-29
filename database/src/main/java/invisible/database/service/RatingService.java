package invisible.database.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import invisible.database.models.criteria.CategorieRating;
import invisible.database.models.objects.PointOfInterest;
import invisible.database.models.criteria.QuestionRatingObject;
import invisible.database.models.enums.RatingOptions;
import invisible.database.models.objects.PreviewRatingDTO;
import invisible.database.models.objects.Rating;
import invisible.database.repository.CultureEntryRepository;
import invisible.database.repository.PointOfInterestRepository;
import invisible.database.repository.QuestionRepository;
import invisible.database.repository.RatingRepository;
import invisible.database.repository.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Project:        In_Visible
 * <p>
 * Author:         Moritz Thomas
 * <p>
 * Creation date:  27.12.2019
 * <p>
 * <p/>
 */
@Service
public class RatingService {

  private final String rating_sequence = "rating_sequence";

  private final RatingRepository ratingRepository;
  private final PointOfInterestRepository pointOfInterestRepository;
  private final SequenceGeneratorService sequenceGeneratorService;
  private final QuestionRepository questionRepository;
  private final UserRepository userRepository;

  public RatingService(RatingRepository ratingRepository, PointOfInterestRepository pointOfInterestRepository, CultureEntryRepository entryRepository, SequenceGeneratorService sequenceGeneratorService, QuestionRepository questionRepository, UserRepository userRepository) {
    this.ratingRepository = ratingRepository;
    this.pointOfInterestRepository = pointOfInterestRepository;
    this.sequenceGeneratorService = sequenceGeneratorService;
    this.questionRepository = questionRepository;
    this.userRepository = userRepository;
  }

  public List<Rating> getAllRatingsForPoi(Long poiId) {
    return ratingRepository.findAllByPoiId(poiId);
  }

//  public List<Rating> getAllRatingsForEntry(Long entryID) {
//    Optional<Entry> byId = entryRepository.findById(entryID);
//    return byId.map(ratingRepository::findAllByEntry).orElse(null);
//  }

  /**
   * Receives rating to save, checks for existing ratings by user and updates the POI's most important questions with the changed overall rating
   * @param rating
   * @return
   */
  public Long postRating(Rating rating) {
    Optional<PointOfInterest> byId = pointOfInterestRepository.findById(rating.getPoiId());
    if(byId.isPresent()) {
      List<QuestionRatingObject> overallRatingsForQuestions = new ArrayList<>();
      List<Rating> allByUserIdAndPoiId = ratingRepository.findAllByUserIdAndPoiId(rating.getUserId(), rating.getPoiId());
      if(allByUserIdAndPoiId.size() > 0) {
        return null;
      } else {
        rating.setId(sequenceGeneratorService.generateId(rating_sequence));
        rating.setCreationDate(new Date());
        rating.setLastUpdated(new Date());

        if( rating.getCategorieRatings().size() > 0){
          rating.getCategorieRatings().forEach(categorieRating -> overallRatingsForQuestions.add(
              new QuestionRatingObject(
                  categorieRating.getQuestionId(),
                  getOverallRatingForQuestion(categorieRating.getQuestionId())
              )
          ));
        }
        byId.get().setOverallRatingPerQuestion(overallRatingsForQuestions);
        pointOfInterestRepository.save(byId.get());
        return ratingRepository.save(rating).getId();
      }
    } else {
      return null;
    }
  }

  public List<Rating> getNewestRatingForPoi(Long poiID) {
    return ratingRepository.findByPoiIdOrderByLastUpdatedDesc(poiID).stream()
        .peek(rating -> rating.setUser(userRepository.findById(rating.getUserId()).orElse(null)))
        .collect(Collectors.toList());
  }

//  public List<Rating> getNewestRatingForEntry(Long entryID) {
//    Optional<Entry> byId = entryRepository.findById(entryID);
//    return byId.map(ratingRepository::findByEntryOrderByLastUpdatedDesc).orElse(null);
//  }

  private Float getOverallRatingForQuestion(Long questionId) {
      List<CategorieRating> allCategoryRatings = ratingRepository.findAll().stream()
          .map(Rating::getCategorieRatings)
          .flatMap(List::stream)
          .filter(categorieRating -> Objects.equals(categorieRating.getQuestionId(), questionId))
          .collect(Collectors.toList());
      if(allCategoryRatings.size() == 0) {
        return 0F;
      }
      List<CategorieRating> positiveCategoryRatings = allCategoryRatings.stream()
          .filter(categorieRating -> categorieRating.getRating() == RatingOptions.YES)
          .collect(Collectors.toList());
      if(positiveCategoryRatings.size() > 0 ) {
        return (float) (allCategoryRatings.size() / positiveCategoryRatings.size());
      } else return 0F;
  }

  public ResponseEntity<Rating> getRating(Long ratingId) {
    Optional<Rating> byId = ratingRepository.findById(ratingId);
    return byId.map(rating -> new ResponseEntity<>(rating, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

//  unfinished
  public ResponseEntity<List<PreviewRatingDTO>> getTopQuestionsForPoi(Long poiID) {

//    current top 7 questions - should be 5 but the first is seperated into 3
    List<Long> questionIds = new ArrayList<>(Arrays.asList(1L, 2L, 3L, 7L, 25L, 32L, 40L));
    Map<Long, List<CategorieRating>> ratingMap = questionIds.stream().collect(Collectors.toMap(Long::longValue, List -> new ArrayList<>()));
    List<CategorieRating> categorieRatingList = ratingRepository.findByPoiId(poiID).stream()
        .map(Rating::getCategorieRatings)
        .flatMap(List::stream)
        .filter(categorieRating -> questionIds.contains(categorieRating.getQuestionId()))
        .peek(categorieRating -> {
          if (categorieRating.getRating() == RatingOptions.YES) {
            ratingMap.get(categorieRating.getQuestionId()).add(categorieRating);
          }
        })
        .collect(Collectors.toList());
    return new ResponseEntity<>(questionIds.stream()
        .map(aLong -> new PreviewRatingDTO(
            (float) (ratingMap.get(aLong).size() /
                            categorieRatingList.stream()
                                .filter(categorieRating -> categorieRating.getQuestionId().equals(aLong))
                                .count()),
              questionRepository.findById(aLong).get()))
        .collect(Collectors.toList()),HttpStatus.OK);
  }
}
