package invivible.database.models.criteria;

import org.springframework.data.mongodb.core.mapping.Document;

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
public class CriteriaObject {

  private String name;
  private long criteria_rating;
  private String criteria_text;
}
