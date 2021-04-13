package bg.softuni.cooking.web;

import bg.softuni.cooking.model.binding.ArticleAddBindingModel;
import bg.softuni.cooking.model.service.ArticleServiceModel;
import bg.softuni.cooking.model.view.ArticleViewModel;
import bg.softuni.cooking.service.ArticleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/articles")
public class BlogController {

    private final ArticleService articleService;
    private final ModelMapper modelMapper;

    public BlogController(ArticleService articleService, ModelMapper modelMapper) {
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
                             BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("articleAddBindingModel", ArticleAddBindingModel.class);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.articleAddBindingModel", bindingResult);
            return "redirect:add";
        }



        articleService.addArticle(modelMapper.map(articleAddBindingModel, ArticleServiceModel.class));

        return "redirect:/";

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

}
