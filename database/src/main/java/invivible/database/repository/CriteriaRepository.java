package invivible.database.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import invivible.database.models.criteria.Criteria;
import invivible.database.models.user.User;

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
public interface CriteriaRepository extends MongoRepository<Criteria, Long> {
}
