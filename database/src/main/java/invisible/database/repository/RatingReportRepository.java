package invisible.database.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import invisible.database.models.objects.RatingReport;

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
public interface RatingReportRepository extends MongoRepository<RatingReport, Long> {

  List<RatingReport> findRatingReportsBySolvedFalse();
}
