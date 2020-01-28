package invisible.database.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import invisible.database.models.criteria.CategoryFormDto;
import invisible.database.models.criteria.Question;
import invisible.database.models.criteria.SubCategory;
import invisible.database.models.criteria.Category;
import invisible.database.service.CategoryService;

/**
 * Project:        In_Visible
 * <p>
 * Author:         Moritz Thomas
 * <p>
 * Creation date:  27.12.2019
 * <p>
 * <p/>
 */
@RestController
@RequestMapping("/db/criteria")
public class CategoryController {

  private final CategoryService categoryService;

  public CategoryController(CategoryService criteriaService) {
    this.categoryService = criteriaService;
  }

  @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CategoryFormDto> getForm() {
    CategoryFormDto form = categoryService.getForm();
    if(form != null){
      return new ResponseEntity<>(form, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> postCategory(@RequestBody Category category) {
    Long categoryId = categoryService.postCategory(category);
    if(categoryId != null) {
      return new ResponseEntity<>("Category saved with Id: " + categoryId, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping(value = "/subCategory", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> postSubCategory(@RequestBody SubCategory subCategory) {
    Long subCategoryId = categoryService.postSubCategory(subCategory);
    if(subCategoryId != null) {
      return new ResponseEntity<>("SubCategory saved with Id: " + subCategoryId, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping(value = "/question", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> postQuestion(@RequestBody Question question) {
    Long questionId = categoryService.postQuestion(question);
    if(questionId != null) {
      return new ResponseEntity<>("Question saved with Id: " + questionId, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }
}
