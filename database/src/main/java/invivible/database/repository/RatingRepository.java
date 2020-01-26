package invivible.database.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import invivible.database.models.objects.PointOfInterest;
import invivible.database.models.objects.Rating;

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
@Service
public interface RatingRepository extends MongoRepository<Rating, Long> {

  List<Rating> findAllByPoiId(Long poiId);

  List<Rating> findAllByUserIdAndPoiId(Long userId, Long poiId);

//  List<Rating> findAllByEntry(Entry entry);

  List<Rating> findByPoiIdOrderByLastUpdatedDesc(Long poiId);

//  List<Rating> findByEntryOrderByLastUpdatedDesc(Entry entry);

}
