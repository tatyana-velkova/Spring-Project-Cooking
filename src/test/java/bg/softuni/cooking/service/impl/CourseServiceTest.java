package bg.softuni.cooking.service.impl;

import bg.softuni.cooking.model.entity.Course;
import bg.softuni.cooking.model.view.CourseViewModel;
import bg.softuni.cooking.repository.CourseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    private CourseServiceImpl courseServiceToTest;
    private Course course1, course2;
    private ModelMapper modelMapper;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CourseRepository courseRepository;

    @BeforeEach
    public void init(){
        courseServiceToTest = new CourseServiceImpl(courseRepository, new ModelMapper());

        course1 = new Course();
        course1.setName("Fondue");
        course1.setStartDate(LocalDate.of(2021, 5,1));
        course1.setEndDate(LocalDate.of(2021, 5, 2));
        course1.setLocation("Sofia");
        course1.setParticipants(10);
        course1.setOrganiserName("Organiser1");
        course1.setOrganiserEmail("organiser1@test.bg");
        courseRepository.save(course1);

        course2 = new Course();
        course2.setName("Cakes");
        course2.setStartDate(LocalDate.of(2021, 5,1));
        course2.setEndDate(LocalDate.of(2021, 5, 2));
        course2.setLocation("Sofia");
        course2.setParticipants(5);
        course2.setOrganiserName("Organiser2");
        course2.setOrganiserEmail("organiser2@test.bg");
        courseRepository.save(course2);
    }

    @Test
    public void testFindAll(){
        Mockito.when(courseRepository.findAllCourses())
                .thenReturn(List.of(course1, course2));

        List<CourseViewModel> allViewModels = courseServiceToTest.findAllCourses();
        Assertions.assertEquals(2, allViewModels.size());

        CourseViewModel course1 = allViewModels.get(0);
        CourseViewModel course2 = allViewModels.get(1);

        Assertions.assertEquals("Cakes", course2.getName());
        Assertions.assertEquals("Sofia", course2.getLocation());
        Assertions.assertEquals("2021-05-01", course2.getStartDate());
        Assertions.assertEquals("2021-05-02", course2.getEndDate());
        Assertions.assertEquals(5, course2.getParticipants());
        Assertions.assertEquals("Organiser2", course2.getOrganiserName());
        Assertions.assertEquals("organiser2@test.bg", course2.getOrganiserEmail());

        Assertions.assertEquals("Fondue", course1.getName());
        Assertions.assertEquals("Sofia", course1.getLocation());
        Assertions.assertEquals("2021-05-01", course1.getStartDate());
        Assertions.assertEquals("2021-05-02", course1.getEndDate());
        Assertions.assertEquals(10, course1.getParticipants());
        Assertions.assertEquals("Organiser1", course1.getOrganiserName());
        Assertions.assertEquals("organiser1@test.bg", course1.getOrganiserEmail());

    }


}
