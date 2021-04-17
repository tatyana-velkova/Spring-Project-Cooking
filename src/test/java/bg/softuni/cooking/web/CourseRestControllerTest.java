package bg.softuni.cooking.web;

import bg.softuni.cooking.model.entity.Course;
import bg.softuni.cooking.model.entity.User;
import bg.softuni.cooking.repository.CourseRepository;
import bg.softuni.cooking.repository.LogRepository;
import bg.softuni.cooking.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class CourseRestControllerTest {

    private Long testCourseId;


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CourseRepository mockCourseRepository;

    @Autowired
    private UserRepository mockUserRepository;

    @Autowired
    private LogRepository mockLogRepository;

    @BeforeEach
    public void setUp(){
        mockCourseRepository.deleteAll();
        this.init();
    }

    @AfterEach
    void cleanUp(){
        mockCourseRepository.deleteAll();
    }

    @Test
    @WithMockUser(value = "ivan", roles = {"USER", "ADMIN"})
    void testFetchCourses() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/courses/api"))
                .andExpect(status().isOk())
        .andExpect(jsonPath("[0].name").value("Bread techniques"))
        .andExpect(jsonPath("[0].participants").value(5))
        .andExpect(jsonPath("[0].organiserEmail").value("testmail@test.bg"));


    }

    void init(){
        User user = new User();
        user.setFirstName("Ivan");
        user.setLastName("Ivanov");
        user.setUsername("ivan");
        user.setPassword("123");
        user.setEmail("ivan@est.bg");
        mockUserRepository.save(user);

        Course course = new Course();
        course.setName("Bread techniques");
        course.setOrganiserEmail("testmail@test.bg");
        course.setOrganiserName("organiser");
        course.setParticipants(5);
        course.setLocation("Sofia");
        course.setStartDate(LocalDate.of(2021, 5, 1));
        course.setEndDate(LocalDate.of(2021, 5, 7));
        mockCourseRepository.save(course);

        testCourseId = course.getId();
    }
}
