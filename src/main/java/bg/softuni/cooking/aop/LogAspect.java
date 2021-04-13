package bg.softuni.cooking.aop;

import bg.softuni.cooking.service.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {
    private final LogService logService;

    public LogAspect(LogService logService) {
        this.logService = logService;
    }

    @Pointcut("execution(* bg.softuni.cooking.web.RecipeController.recipeDetails(..))")
    public void detailsPointcut(){};

    @After("detailsPointcut()")
    public void afterAdvice(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();

        Long recipeId = (Long) args[0];
        String action = joinPoint.getSignature().getName();
        logService.createLog(action, recipeId);

    }
}
