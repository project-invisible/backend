package invivible.database.service;

import org.springframework.stereotype.Service;

import invivible.database.models.criteria.CategorieRating;
import invivible.database.models.criteria.Question;
import invivible.database.models.criteria.QuestionRatingObject;
import invivible.database.models.enums.RatingOptions;
import invivible.database.models.objects.PointOfInterest;
import invivible.database.models.objects.Rating;
import invivible.database.repository.CultureEntryRepository;
import invivible.database.repository.PointOfInterestRepository;
import invivible.database.repository.QuestionRepository;
import invivible.database.repository.RatingRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

  public RatingService(RatingRepository ratingRepository, PointOfInterestRepository pointOfInterestRepository, CultureEntryRepository entryRepository, SequenceGeneratorService sequenceGeneratorService, QuestionRepository questionRepository) {
    this.ratingRepository = ratingRepository;
    this.pointOfInterestRepository = pointOfInterestRepository;
    this.sequenceGeneratorService = sequenceGeneratorService;
    this.questionRepository = questionRepository;
  }

  public List<Rating> getAllRatingsForPoi(Long poiId) {
    Optional<PointOfInterest> byId = pointOfInterestRepository.findById(poiId);
    return byId.map(pointOfInterest -> ratingRepository.findAllByPoiId(pointOfInterest.getId())).orElse(null);
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
    Optional<PointOfInterest> byId = pointOfInterestRepository.findById(rating.getPoi().getId());
    if(byId.isPresent()) {
      List<QuestionRatingObject> overallRatingsForQuestions = new ArrayList<>();
      List<Rating> allByUserIdAndPoiId = ratingRepository.findAllByUserIdAndPoiId(rating.getUser().getId(), rating.getPoi().getId());
      if(allByUserIdAndPoiId.size() > 0) {
        return null;
      } else {
        rating.setId(sequenceGeneratorService.generateId(rating_sequence));
        rating.setCreationDate(new Date());
        rating.setLastUpdated(new Date());

        rating.getCategorieRatings().forEach(categorieRating -> overallRatingsForQuestions.add(
            new QuestionRatingObject(
                categorieRating.getQuestion(),
                getOverallRatingForQuestion(categorieRating.getQuestion())
            )
        ));
        byId.get().setOverallRatingPerQuestion(overallRatingsForQuestions);
        pointOfInterestRepository.save(byId.get());
        return ratingRepository.save(rating).getId();
      }
    } else {
      return null;
    }
  }

  public List<Rating> getNewestRatingForPoi(Long poiID) {
    Optional<PointOfInterest> byId = pointOfInterestRepository.findById(poiID);
    return byId.map(ratingRepository::findByPoiOrderByLastUpdatedDesc).orElse(null);
  }

//  public List<Rating> getNewestRatingForEntry(Long entryID) {
//    Optional<Entry> byId = entryRepository.findById(entryID);
//    return byId.map(ratingRepository::findByEntryOrderByLastUpdatedDesc).orElse(null);
//  }

  private Float getOverallRatingForQuestion(Question question) {
    List<CategorieRating> allCategoryRatings = ratingRepository.findAll().stream()
        .map(Rating::getCategorieRatings)
        .flatMap(List::stream)
        .filter(categorieRating -> categorieRating.getQuestion() == question)
        .collect(Collectors.toList());
    List<CategorieRating> positiveCategoryRatings = allCategoryRatings.stream()
        .filter(categorieRating -> categorieRating.getRating() == RatingOptions.YES)
        .collect(Collectors.toList());
    return (float) (allCategoryRatings.size() / positiveCategoryRatings.size());
  }
}
