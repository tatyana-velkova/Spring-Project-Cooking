package bg.softuni.cooking.web;

import bg.softuni.cooking.model.binding.ArticleAddBindingModel;
import bg.softuni.cooking.model.service.ArticleServiceModel;
import bg.softuni.cooking.model.view.ArticleViewModel;
import bg.softuni.cooking.service.ArticleService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;
    private final ModelMapper modelMapper;

    public ArticleController(ArticleService articleService, ModelMapper modelMapper) {
        this.articleService = articleService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    public String add(Model model){
        if (!model.containsAttribute("articleAddBindingModel")){
            model.addAttribute("articleAddBindingModel", new ArticleAddBindingModel());
        }
        return "article-add";
    }

    @PostMapping("/add")
    public String addConfirm(@Valid @ModelAttribute ArticleAddBindingModel articleAddBindingModel,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes,
                             @AuthenticationPrincipal UserDetails principal){
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("articleAddBindingModel", ArticleAddBindingModel.class);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.articleAddBindingModel", bindingResult);
            return "redirect:add";
        }

        ArticleServiceModel articleServiceModel = modelMapper.map(articleAddBindingModel, ArticleServiceModel.class);
        articleServiceModel.setUser(principal.getUsername());



        articleService.addArticle(articleServiceModel);

        return "redirect:/home";

    }


    @GetMapping("/read/{id}")
    public String readArticle(Model model, @PathVariable Long id){
        ArticleViewModel articleViewModel = articleService.findArticleById(id);
        model.addAttribute("articleDetails", articleViewModel);
        return "article-detail";
    }


    @GetMapping("/list")
    public String listArticles(Model model){
        if (articleService.findAllArticles().size() == 0){
            model.addAttribute("noArticles", true);
        } else {
            model.addAttribute("allArticles", articleService.findAllArticles());
        }
        return "view-articles";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id){
        articleService.deleteById(id);

        return "redirect:/home";
    }

}
