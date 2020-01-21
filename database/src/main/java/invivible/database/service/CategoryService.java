package invivible.database.service;

import org.springframework.stereotype.Service;

import invivible.database.models.criteria.Category;
import invivible.database.models.criteria.CategoryFormDto;
import invivible.database.models.criteria.Question;
import invivible.database.models.criteria.SubCategory;
import invivible.database.repository.CategoryRepository;
import invivible.database.repository.QuestionRepository;
import invivible.database.repository.SubCategoryRepository;

import java.util.List;

/**
 * Project:        In_Visible
 * <p>
 * Author:         Moritz Thomas
 * <p>
 * Creation date:  27.12.2019
 * <p>
 * <p/>
 */
@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;
  private final SubCategoryRepository subCategoryRepository;
  private final QuestionRepository questionRepository;

  public CategoryService(CategoryRepository categoryRepository, SubCategoryRepository subCategoryRepository, QuestionRepository questionRepository) {
    this.categoryRepository = categoryRepository;
    this.subCategoryRepository = subCategoryRepository;
    this.questionRepository = questionRepository;
  }

  public CategoryFormDto getForm() {
    List<Category> allCategories = categoryRepository.findAll();
    List<SubCategory> allSubCategories = subCategoryRepository.findAll();
    List<Question> allQuestions = questionRepository.findAll();
    return new CategoryFormDto(allCategories, allSubCategories, allQuestions);
  }

  public Long postCategory(Category category) {
    if(!category.getName().equals("")){
      return categoryRepository.save(category).getId();
    } else {
      return null;
    }
  }

  public Long postSubCategory(SubCategory subCategory) {
    if(!subCategory.getName().equals("")){
      return subCategoryRepository.save(subCategory).getId();
    } else {
      return null;
    }
  }

  public Long postQuestion(Question question) {
    if(!question.getText().equals("")){
      return questionRepository.save(question).getId();
    } else {
      return null;
    }
  }
}
