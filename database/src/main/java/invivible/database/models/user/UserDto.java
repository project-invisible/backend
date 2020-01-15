package invivible.database.models.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Project:        In_Visible
 * <p>
 * Author:         Moritz Thomas
 * <p>
 * Creation date:  17.12.2019
 * <p>
 * <p/>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

  @NotNull
  @NotEmpty
  private Long id;
  @NotEmpty
  @NotNull
  private String email;
  private String username;
  @NotEmpty
  @NotNull
  private String password;
}
