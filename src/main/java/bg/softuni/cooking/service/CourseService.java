package bg.softuni.cooking.service;

import bg.softuni.cooking.model.service.CourseServiceModel;
import bg.softuni.cooking.model.view.CourseViewModel;

import java.util.List;

public interface CourseService {
    void addCourse(CourseServiceModel courseServiceModel);

    List<CourseViewModel> findAllCourses();


    void bookCourseById(Long id);

    void seedCourses();
}
