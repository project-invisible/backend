package invivible.database.models.objects;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import invivible.database.models.helper.Coordinates;
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
@Document(collection = "entry")
//Todo maybe rename to something more descriptive
public class Entry {

  @Id
  private Long id;
  private String name;
  private String description;
  private Coordinates coords;
  private Date creationDate;
  private User user;
  private Binary image;
}
