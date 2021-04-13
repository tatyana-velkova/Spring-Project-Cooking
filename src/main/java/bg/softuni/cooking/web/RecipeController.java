package bg.softuni.cooking.web;

import bg.softuni.cooking.model.binding.RecipeAddBindingModel;
import bg.softuni.cooking.model.entity.CategoryEnum;
import bg.softuni.cooking.model.service.RecipeServiceModel;
import bg.softuni.cooking.model.view.RecipeDetailViewModel;
import bg.softuni.cooking.service.CategoryService;
import bg.softuni.cooking.service.RecipeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/recipes")
public class RecipeController {
    private final RecipeService recipeService;
    private final ModelMapper modelMapper;
    private final CategoryService categoryService;

    public RecipeController(RecipeService recipeService, ModelMapper modelMapper, CategoryService categoryService) {
        this.recipeService = recipeService;
        this.modelMapper = modelMapper;
        this.categoryService = categoryService;
    }

    @GetMapping("/add")
    public String add(Model model){
        if (!model.containsAttribute("recipeAddBindingModel")){
            model.addAttribute("recipeAddBindingModel", new RecipeAddBindingModel());
            model.addAttribute("allCategories", this.categoryService.findAllCategories());
        }
        return "recipe-add";
    }

    @PostMapping("/add")
    public String addConfirm(@Valid @ModelAttribute RecipeAddBindingModel recipeAddBindingModel,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("recipeAddBindingModel", recipeAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.recipeAddBindingModel", bindingResult);
            redirectAttributes.addFlashAttribute("allCategories", this.categoryService.findAllCategories());
            return "redirect:add";
        }

        RecipeServiceModel recipeServiceModel = modelMapper.map(recipeAddBindingModel, RecipeServiceModel.class);
        recipeService.addRecipe(recipeServiceModel);

        return "redirect:/home";
    }




    @GetMapping("/desserts")
    public String desserts(Model model){
        if (recipeService.findRecipesByCategoryEnum(CategoryEnum.valueOf("DESSERT")).size() == 0){
            model.addAttribute("noRecipes", true);
        }else {
            model.addAttribute("allDesserts", recipeService.findRecipesByCategoryEnum(CategoryEnum.valueOf("DESSERT")));
        }

        return "view-desserts";
    }

    @GetMapping("/salads")
    public String salads(Model model){
        if (recipeService.findRecipesByCategoryEnum(CategoryEnum.valueOf("SALAD")).size() == 0){
            model.addAttribute("noRecipes", true);
        }else {
            model.addAttribute("allSalads", recipeService.findRecipesByCategoryEnum(CategoryEnum.valueOf("SALAD")));
        }
        return "view-salads";
    }

    @GetMapping("/mainDishes")
    public String mainDishes(Model model){
        if (recipeService.findRecipesByCategoryEnum(CategoryEnum.valueOf("MAIN_DISH")).size() == 0){
            model.addAttribute("noRecipes", true);
        } else {
            model.addAttribute("allMainDishes", recipeService.findRecipesByCategoryEnum(CategoryEnum.valueOf("MAIN_DISH")));
        }

        return "view-main-dishes";
    }

    @GetMapping("/sushi")
    public String sushi(Model model){
        if(recipeService.findRecipesByCategoryEnum(CategoryEnum.valueOf("SUSHI")).size() == 0){
            model.addAttribute("noRecipes", true);
        }else {
            model.addAttribute("allSushi", recipeService.findRecipesByCategoryEnum(CategoryEnum.valueOf("SUSHI")));
        }

        return "view-sushi";
    }

    @GetMapping("/pizza")
    public String pizza(Model model){
        if (recipeService.findRecipesByCategoryEnum(CategoryEnum.valueOf("PIZZA")).size() == 0){
            model.addAttribute("noRecipes", true);
        }else {
            model.addAttribute("allPizza", recipeService.findRecipesByCategoryEnum(CategoryEnum.valueOf("PIZZA")));
        }
        return "view-pizza";
    }

    @GetMapping("/details/{id}")
    public String recipeDetails(@PathVariable Long id, Model model){
        RecipeDetailViewModel recipeDetailViewModel = recipeService.findRecipeById(id);
        model.addAttribute("recipeDetails", recipeDetailViewModel);
        return "details-recipe";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id){
        recipeService.deleteById(id);

        return "redirect:/home";
    }



}
