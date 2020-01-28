package invisible.database.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import invisible.database.models.objects.Rating;

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

  List<Rating> findByPoiId(Long poiId);
//  List<Rating> findByEntryOrderByLastUpdatedDesc(Entry entry);

}
