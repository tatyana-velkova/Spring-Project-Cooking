package bg.softuni.cooking.web;

import bg.softuni.cooking.model.view.RecipeDetailViewModel;
import bg.softuni.cooking.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final RecipeService recipeService;

    public HomeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/")
    public String index(Model model){
        return "index";
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }

}
