package invivible.database.service;

import org.springframework.stereotype.Service;

import invivible.database.models.objects.PointOfInterest;
import invivible.database.models.objects.Rating;
import invivible.database.repository.EntryRepository;
import invivible.database.repository.PointOfInterestRepository;
import invivible.database.repository.RatingRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
  private final EntryRepository entryRepository;
  private final SequenceGeneratorService sequenceGeneratorService;

  public RatingService(RatingRepository ratingRepository, PointOfInterestRepository pointOfInterestRepository, EntryRepository entryRepository, SequenceGeneratorService sequenceGeneratorService) {
    this.ratingRepository = ratingRepository;
    this.pointOfInterestRepository = pointOfInterestRepository;
    this.entryRepository = entryRepository;
    this.sequenceGeneratorService = sequenceGeneratorService;
  }

  public List<Rating> getAllRatingsForPoi(Long poiId) {
    Optional<PointOfInterest> byId = pointOfInterestRepository.findById(poiId);
    return byId.map(pointOfInterest -> ratingRepository.findAllByPoiId(pointOfInterest.getId())).orElse(null);
  }

//  public List<Rating> getAllRatingsForEntry(Long entryID) {
//    Optional<Entry> byId = entryRepository.findById(entryID);
//    return byId.map(ratingRepository::findAllByEntry).orElse(null);
//  }

  public Long postRating(Rating rating) {
    List<Rating> allByUserIdAndPoiId = ratingRepository.findAllByUserIdAndPoiId(rating.getUser().getId(), rating.getPoi().getId());
    if(allByUserIdAndPoiId.size() > 0) {
      return null;
    } else {
      rating.setId(sequenceGeneratorService.generateId(rating_sequence));
      rating.setCreationDate(new Date());
      rating.setLastUpdated(new Date());
      return ratingRepository.save(rating).getId();
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
}
