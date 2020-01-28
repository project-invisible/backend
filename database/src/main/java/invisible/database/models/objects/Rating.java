package invisible.database.models.objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import invisible.database.models.criteria.CategorieRating;
import invisible.database.models.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "rating")
public class Rating {

  @Id
  private Long id;
  private Long userId;
  @Transient
  private User user;
  private Long poiId;
  private String generalComment;
  private List<CategorieRating> categorieRatings;
  private Date creationDate;
  private Date lastUpdated;

}
