package bg.softuni.cooking.repository;

import bg.softuni.cooking.model.entity.CategoryEnum;
import bg.softuni.cooking.model.entity.Recipe;
import bg.softuni.cooking.model.view.RecipeViewModel;
import bg.softuni.cooking.service.RecipeService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findByCategory_CategoryName(CategoryEnum categoryEnum);

    Optional<Recipe> findById(Long id);

    @Query("SELECT r FROM Recipe r")
    List<Recipe> findAllRecipes();


}
