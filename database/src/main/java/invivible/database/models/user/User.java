package invivible.database.models.user;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import invivible.database.helper.ValidEmail;
import invivible.database.models.enums.Role;
import invivible.database.models.enums.Status;
import invivible.database.models.helper.Contact;
import invivible.database.models.objects.Rating;
import invivible.database.models.objects.UserReport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
public class User {

  @Id
  private Long id;
  @NotNull
  @NotEmpty
  @ValidEmail
  private String email;
  @NotNull
  @NotEmpty
  private String username;
  private List<Rating> ratings;
  private boolean anonymous;
  private Role role;
  private Date creation_date;
  @NotNull
  @NotEmpty
  private String password;
  private Contact contact;
  private Status status;
  private Binary image;
}
