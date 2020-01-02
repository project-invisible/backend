package invivible.auth.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Project:        ProjektPool
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

  @NotEmpty
  @NotNull
  private String email;
  private String username;
  @NotEmpty
  @NotNull
  private String password;
}
