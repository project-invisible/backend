package invisible.database.models.user;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import invisible.database.models.enums.Role;
import invisible.database.models.enums.Status;
import invisible.database.models.helper.Contact;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.Date;

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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
public class User {

  @Id
  private Long id;
  @NotNull
  @NotEmpty
  @Email(message = "No valid email")
  private String email;
  private String username;
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
