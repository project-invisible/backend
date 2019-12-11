package invivible.database.models.helper;

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
public class Coordinates {

  private Float x;
  private Float y;
}
