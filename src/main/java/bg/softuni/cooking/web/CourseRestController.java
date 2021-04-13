package bg.softuni.cooking.web;

import bg.softuni.cooking.model.view.CourseViewModel;
import bg.softuni.cooking.service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/courses")
public class CourseRestController {
    private final CourseService courseService;
    private final ModelMapper modelMapper;

    public CourseRestController(CourseService courseService, ModelMapper modelMapper) {
        this.courseService = courseService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/api")
    public ResponseEntity<List<CourseViewModel>> findAll(Model model){
        List<CourseViewModel> courseViewModels = courseService.findAllCourses();

        return ResponseEntity.ok().body(courseViewModels);
    }
}
