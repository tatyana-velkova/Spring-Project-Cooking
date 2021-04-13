package bg.softuni.cooking.service.impl;

import bg.softuni.cooking.model.entity.Course;
import bg.softuni.cooking.model.service.CourseServiceModel;
import bg.softuni.cooking.model.view.CourseViewModel;
import bg.softuni.cooking.repository.CourseRepository;
import bg.softuni.cooking.service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    public CourseServiceImpl(CourseRepository courseRepository, ModelMapper modelMapper) {
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addCourse(CourseServiceModel courseServiceModel) {
        courseRepository.save(modelMapper.map(courseServiceModel, Course.class));
    }

    @Override
    public List<CourseViewModel> findAllCourses() {
        return courseRepository.findAllCourses()
                .stream().map(course -> modelMapper.map(course, CourseViewModel.class)).collect(Collectors.toList());
    }

    @Override
    public void bookCourseById(Long id) {
        Course course = courseRepository.findById(id).orElse(null);

        course.setParticipants(course.getParticipants() - 1);

        courseRepository.save(course);


    }

    @Override
    public void seedCourses() {
        if (courseRepository.count() == 0){
            Course course1 = new Course();
            course1.setName("Raw Cakes");
            course1.setParticipants(10);
            course1.setLocation("Sofia, Ivan Rilski str.7");
            course1.setStartDate(LocalDate.of(2021, 5, 2));
            course1.setEndDate(LocalDate.of(2021, 5, 3));
            course1.setOrganiserName("Chef Marchev");
            course1.setOrganiserEmail("marchev@test.bg");

            courseRepository.save(course1);

            Course course2 = new Course();
            course2.setName("Italian Desserts");
            course2.setParticipants(8);
            course2.setLocation("Sofia, Ivan Rilski str.7");
            course2.setStartDate(LocalDate.of(2021, 5, 21));
            course2.setEndDate(LocalDate.of(2021, 5, 21));
            course2.setOrganiserName("Chef Rangelov");
            course2.setOrganiserEmail("rangelov@test.bg");

            courseRepository.save(course2);

            Course course3 = new Course();
            course3.setName("Mediterranean specialties");
            course3.setParticipants(6);
            course3.setLocation("Sofia, Tsar Boris III 85A");
            course3.setStartDate(LocalDate.of(2021, 6, 1));
            course3.setEndDate(LocalDate.of(2021, 6, 14));
            course3.setOrganiserName("Chef Marchev");
            course3.setOrganiserEmail("marchev@test.bg");

            courseRepository.save(course3);
        }

    }
}
