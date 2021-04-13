package bg.softuni.cooking.web;

import bg.softuni.cooking.model.binding.RoleAddBindingModel;
import bg.softuni.cooking.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/roles")
public class RoleController {
    private final UserService userService;

    public RoleController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/add")
    public ModelAndView add(ModelAndView modelAndView){
        modelAndView.addObject("usernames", userService.findAllUsernames());
        modelAndView.setViewName("role-add");
        return modelAndView;
    }


    @PostMapping("/add")
    public String addConfirm(@Valid @ModelAttribute("roleAddBindingModel") RoleAddBindingModel roleAddBindingModel){
        this.userService.changeUserRole(roleAddBindingModel.getUsername(), roleAddBindingModel.getRole());
        return "redirect:/home";
    }
}
