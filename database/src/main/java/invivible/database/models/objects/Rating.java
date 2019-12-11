package invivible.database.models.objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import invivible.database.models.criteria.Criteria;
import invivible.database.models.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "rating")
public class Rating {

  @Id
  private long id;
  private User user;
  private PointOfInterest poi;
  private Float overall_rating;
  private List<Criteria> criteria_ratings;
  private Date creation_date;
  private Date last_updated;

}
