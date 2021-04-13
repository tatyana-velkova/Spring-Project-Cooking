package bg.softuni.cooking.service;

import bg.softuni.cooking.model.entity.CategoryEnum;
import bg.softuni.cooking.model.service.RecipeServiceModel;
import bg.softuni.cooking.model.view.RecipeDetailViewModel;
import bg.softuni.cooking.model.view.RecipeViewModel;

import java.util.List;

public interface RecipeService {

    void addRecipe(RecipeServiceModel recipeServiceModel);

    List<RecipeViewModel> findRecipesByCategoryEnum(CategoryEnum categoryEnum);

    RecipeDetailViewModel findRecipeById(Long id);

    List<RecipeViewModel> findAllRecipes();

    RecipeServiceModel findById(Long id);

    void seedRecipes();


    void deleteById(Long id);
}
