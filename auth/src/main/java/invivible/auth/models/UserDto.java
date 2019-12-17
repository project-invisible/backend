package invivible.auth.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

  private String email;
  private String username;
  private String password;
}
