package bg.softuni.cooking.web;

import bg.softuni.cooking.model.binding.CourseAddBindingModel;
import bg.softuni.cooking.model.service.CourseServiceModel;
import bg.softuni.cooking.service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;
    private final ModelMapper modelMapper;

    public CourseController(CourseService courseService, ModelMapper modelMapper) {
        this.courseService = courseService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/announce")
    public String announce(Model model){
        if (!model.containsAttribute("courseAddBindingModel")){
            model.addAttribute("courseAddBindingModel", new CourseAddBindingModel());
        }
        return "course-add";
    }

    @PostMapping("/announce")
    public String announceConfirm(@Valid @ModelAttribute CourseAddBindingModel courseAddBindingModel,
                                  BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("courseAddBindingModel", courseAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.courseAddBindingModel", bindingResult);

            return "redirect:announce";
        }

        courseService.addCourse(modelMapper.map(courseAddBindingModel, CourseServiceModel.class));
        return "redirect:/home";
    }

    @GetMapping("/list")
    public String listCourses(Model model){
        return "view-courses";
    }

    @GetMapping("/book/{id}")
    public String bookCourse(@PathVariable Long id){
         courseService.bookCourseById(id);
        return "redirect:/";
    }




}
