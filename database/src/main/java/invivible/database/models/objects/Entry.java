package invivible.database.models.objects;

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
  private long id;
  private String name;
  private String description;
  private Coordinates coords;
  private Date creation_date;
  private User user;
//  todo image
}
