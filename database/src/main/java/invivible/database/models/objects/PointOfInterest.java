package invivible.database.models.objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import invivible.database.models.helper.Coordinates;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Document(collection = "pointOfInterest")
public class PointOfInterest {

  @Id
  private long id;
  private String name;
  private String street;
  private String postal;
  private String city;
  private String description;
  private String email;
  private Coordinates coordinates;
  private Float overall_rating;


}
