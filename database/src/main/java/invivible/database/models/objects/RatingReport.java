package invivible.database.models.objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import invivible.database.models.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
@Document(collection = "ratingReports")
public class RatingReport {

  @Id
  private long id;
  private User reporting_user;
  private Rating rating;
  private Date report_date;
  private boolean solved;
}
