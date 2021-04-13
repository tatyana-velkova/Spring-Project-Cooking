package bg.softuni.cooking.interceptors;


import bg.softuni.cooking.interceptors.UserRegisterInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorWebConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserRegisterInterceptor()).addPathPatterns("/users/register/**");
        registry.addInterceptor(new RecipeAddInterceptor()).addPathPatterns("/recipes/add/**");
        registry.addInterceptor(new CourseAddInterceptor()).addPathPatterns("/courses/announce/**");

    }
}
