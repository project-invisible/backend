package invivible.database.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import invivible.database.models.objects.Entry;
import invivible.database.models.objects.PointOfInterest;
import invivible.database.models.objects.Rating;
import invivible.database.models.user.User;

import java.util.List;

/**
 * Project:        ProjektPool
 * <p>
 * Author:         Moritz Thomas
 * <p>
 * Creation date:  11.12.2019
 * <p>
 * <p/>
 */
@Service
public interface RatingRepository extends MongoRepository<Rating, Long> {

  List<Rating> findAllByPoi(PointOfInterest poi);

  List<Rating> findAllByEntry(Entry entry);

  List<Rating> findByPoiOrderByLastUpdatedDesc(PointOfInterest pointOfInterest);

  List<Rating> findByEntryOrderByLastUpdatedDesc(Entry entry);
}
