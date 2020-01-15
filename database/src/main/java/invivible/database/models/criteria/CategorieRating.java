package invivible.database.models.criteria;

import invivible.database.models.enums.RatingOptions;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Project:        In_Visible
 * <p>
 * Author:         Moritz Thomas
 * <p>
 * Creation date:  15.01.2020
 * <p>
 * <p/>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategorieRating {

  private Long id;
  private Question question;
  private RatingOptions rating;
  private String comment;
  private String tag;
}
