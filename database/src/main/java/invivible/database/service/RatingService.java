package invivible.database.service;

import org.springframework.stereotype.Service;

import invivible.database.models.objects.Entry;
import invivible.database.models.objects.PointOfInterest;
import invivible.database.models.objects.Rating;
import invivible.database.repository.EntryRepository;
import invivible.database.repository.PointOfInterestRepository;
import invivible.database.repository.RatingRepository;

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

  private final RatingRepository ratingRepository;
  private final PointOfInterestRepository pointOfInterestRepository;
  private final EntryRepository entryRepository;

  public RatingService(RatingRepository ratingRepository, PointOfInterestRepository pointOfInterestRepository, EntryRepository entryRepository) {
    this.ratingRepository = ratingRepository;
    this.pointOfInterestRepository = pointOfInterestRepository;
    this.entryRepository = entryRepository;
  }

  public List<Rating> getAllRatingsForPoi(Long poiId) {
    Optional<PointOfInterest> byId = pointOfInterestRepository.findById(poiId);
    return byId.map(ratingRepository::findAllByPoi).orElse(null);
  }

  public List<Rating> getAllRatingsForEntry(Long entryID) {
    Optional<Entry> byId = entryRepository.findById(entryID);
    return byId.map(ratingRepository::findAllByEntry).orElse(null);
  }

  public Long postRating(Rating rating) {
    return ratingRepository.save(rating).getId();
  }

  public List<Rating> getNewestRatingForPoi(Long poiID) {
    Optional<PointOfInterest> byId = pointOfInterestRepository.findById(poiID);
    return byId.map(ratingRepository::findByPoiOrderByLastUpdatedDesc).orElse(null);
  }

  public List<Rating> getNewestRatingForEntry(Long entryID) {
    Optional<Entry> byId = entryRepository.findById(entryID);
    return byId.map(ratingRepository::findByEntryOrderByLastUpdatedDesc).orElse(null);
  }
}
