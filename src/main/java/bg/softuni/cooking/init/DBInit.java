package bg.softuni.cooking.init;

import bg.softuni.cooking.service.CategoryService;
import bg.softuni.cooking.service.CourseService;
import bg.softuni.cooking.service.RecipeService;
import bg.softuni.cooking.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBInit implements CommandLineRunner {

    private final CategoryService categoryService;
    private final UserService userService;
    private final RecipeService recipeService;
    private final CourseService courseService;


    public DBInit(CategoryService categoryService, UserService userService, RecipeService recipeService, CourseService courseService) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.recipeService = recipeService;
        this.courseService = courseService;
    }

    @Override
    public void run(String... args) throws Exception {
        categoryService.initCategories();
        userService.seedUsers();
        recipeService.seedRecipes();
        courseService.seedCourses();

    }
}
