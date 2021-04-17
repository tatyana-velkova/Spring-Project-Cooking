package bg.softuni.cooking.service.impl;

import bg.softuni.cooking.model.entity.Article;
import bg.softuni.cooking.model.entity.User;
import bg.softuni.cooking.model.view.ArticleViewModel;
import bg.softuni.cooking.repository.ArticleRepository;
import bg.softuni.cooking.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {

    private ArticleServiceImpl articleServiceToTest;
    private Article article1, article2;
    private User user1, user2;


    @Mock
    ArticleRepository mockArticleRepository;

    @Mock
    private UserRepository mockUserRepository;


    @BeforeEach
    public void init(){
        articleServiceToTest = new ArticleServiceImpl(mockArticleRepository, new ModelMapper(), mockUserRepository);

        user1 = new User();
        user1.setUsername("user1");

        user2 = new User();
        user2.setUsername("user2");

        mockUserRepository.save(user1);
        mockUserRepository.save(user2);

        article1 = new Article();
        article1.setTitle("Superfood");
        article1.setPublishDate(LocalDate.of(2021, 4, 25));
        article1.setDescription("Description for article1");
        article1.setUser(user1);
        mockArticleRepository.save(article1);

        article2 = new Article();
        article2.setTitle("Important Nutrients");
        article2.setPublishDate(LocalDate.of(2021, 4, 30));
        article2.setDescription("Description of article2");
        article2.setUser(user2);
        mockArticleRepository.save(article2);
    }

    @Test
    public void testFindAll(){
        Mockito.when(mockArticleRepository.findAllArticles())
                .thenReturn(List.of(article1, article2));

        List<ArticleViewModel> allViewModels = articleServiceToTest.findAllArticles();
        Assertions.assertEquals(2, allViewModels.size());

        ArticleViewModel model1 = allViewModels.get(0);
        ArticleViewModel model2 = allViewModels.get(1);

        Assertions.assertEquals("Superfood", article1.getTitle());
        Assertions.assertEquals("Description for article1", article1.getDescription());
        Assertions.assertEquals((LocalDate.of(2021, 4, 25)), article1.getPublishDate());
        Assertions.assertEquals(user1.getUsername(), article1.getUser().getUsername());

        Assertions.assertEquals("Important Nutrients", article2.getTitle());
        Assertions.assertEquals("Description of article2", article2.getDescription());
        Assertions.assertEquals(LocalDate.of(2021, 4, 30), article2.getPublishDate());
        Assertions.assertEquals(user2.getUsername(), article2.getUser().getUsername());
    }
}
