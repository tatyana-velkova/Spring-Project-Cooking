package bg.softuni.cooking.web;

import bg.softuni.cooking.model.entity.Category;
import bg.softuni.cooking.model.entity.CategoryEnum;
import bg.softuni.cooking.model.entity.Recipe;
import bg.softuni.cooking.model.entity.User;
import bg.softuni.cooking.repository.CategoryRepository;
import bg.softuni.cooking.repository.LogRepository;
import bg.softuni.cooking.repository.RecipeRepository;
import bg.softuni.cooking.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class RecipeControllerTest {

    private static final String RECIPE_CONTROLLER_PREFIX = "/recipes";
    private Long testRecipeId;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LogRepository logRepository;


    @Test
    @WithMockUser(value = "ivan", roles = {"USER", "ADMIN"})
    void testShouldReturnValidStatusViewNameAndModel() throws Exception {
        User user = new User();
        user.setFirstName("Ivan");
        user.setLastName("Ivanov");
        user.setUsername("ivan");
        user.setPassword("123");
        user.setEmail("ivanov@est.bg");
        userRepository.save(user);

        Category categorySalad = new Category();
        categorySalad.setCategoryName(CategoryEnum.SALAD);
        categoryRepository.save(categorySalad);

        Recipe recipe = new Recipe();
        recipe.setName("SHOPSKA SALATA");
        recipe.setIngredients("cucumbers, tomatoes, onion, cheese");
        recipe.setImageUrl("https://www.az-jenata.bg/media/az-jenata/files/articles/640x480/8b8c6d7770c85742c969aa623e25a799.jpg");
        recipe.setPreparationMethod("Some information about preparation method");
        recipe.setCategory(categorySalad);
        recipe.setUser(user);

        recipeRepository.save(recipe);
        testRecipeId = recipe.getId();
        mockMvc.perform(MockMvcRequestBuilders.get(
                RECIPE_CONTROLLER_PREFIX + "/details/{id}", testRecipeId
        )).andExpect(status().isOk()).
                andExpect(view().name("details-recipe")).
                andExpect(model().attributeExists("recipeDetails"));
    }


    @Test
    @WithMockUser(value = "ivan1", roles = {"USER", "ADMIN"})
    void addRecipe() throws Exception {
        User user1 = new User();
        user1.setFirstName("Ivan1");
        user1.setLastName("Ivanov1");
        user1.setUsername("ivan1");
        user1.setPassword("123");
        user1.setEmail("ivanov1@est.bg");
        userRepository.save(user1);


        Category categoryMainDish = new Category();
        categoryMainDish.setCategoryName(CategoryEnum.MAIN_DISH);

        mockMvc.perform(MockMvcRequestBuilders.post(
                RECIPE_CONTROLLER_PREFIX + "/add")
        .param("name", "TestRecipe")
        .param("ingredients", "tomatoes, cucumbers, onion, cheese, olives")
        .param("preparationMethod", "Some info about preparation method")
        .param("imageUrl", "https://www.supichka.com/files/images/2956/fit_1400_933.jpg")
        .param("category", categoryMainDish.getCategoryName().name())
                .param("user", user1.getUsername())
        .with(csrf()))
        .andExpect(status().is3xxRedirection());

        List<String> recipeNames = recipeRepository.findAll().stream().map(recipe -> recipe.getName()).collect(Collectors.toList());
        Assertions.assertTrue(recipeNames.contains("TestRecipe"));
    }

    private void init(){

        User user = new User();
        user.setFirstName("Ivan");
        user.setLastName("Ivanov");
        user.setUsername("ivan");
        user.setPassword("123");
        user.setEmail("ivanov@est.bg");
        userRepository.save(user);

        Category categorySalad = new Category();
        categorySalad.setCategoryName(CategoryEnum.SALAD);
        categoryRepository.save(categorySalad);

        Recipe recipe = new Recipe();
        recipe.setName("SHOPSKA SALATA");
        recipe.setIngredients("cucumbers, tomatoes, onion, cheese");
        recipe.setImageUrl("https://www.az-jenata.bg/media/az-jenata/files/articles/640x480/8b8c6d7770c85742c969aa623e25a799.jpg");
        recipe.setPreparationMethod("Some information about preparation method");
        recipe.setCategory(categorySalad);
        recipe.setUser(user);

        recipeRepository.save(recipe);
        testRecipeId = recipe.getId();

    }
}
