package bg.softuni.cooking.service.impl;

import bg.softuni.cooking.model.entity.Category;
import bg.softuni.cooking.model.entity.CategoryEnum;
import bg.softuni.cooking.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    private CategoryServiceImpl categoryServiceToTest;
    Category category1, category2, category3, category4, category5;


    @Mock
    private CategoryRepository mockCategoryRepository;

    @Test
    public void testFindAllCategories(){
        if (mockCategoryRepository.count() > 0) {
            mockCategoryRepository.deleteAll();
        }

        categoryServiceToTest = new CategoryServiceImpl(mockCategoryRepository);
        category1 = new Category();
        category1.setCategoryName(CategoryEnum.SALAD);
        category2 = new Category();
        category2.setCategoryName(CategoryEnum.PIZZA);
        category3 = new Category();
        category3.setCategoryName(CategoryEnum.DESSERT);
        category4 = new Category();
        category4.setCategoryName(CategoryEnum.MAIN_DISH);
        category5 = new Category();
        category5.setCategoryName(CategoryEnum.SUSHI);

        mockCategoryRepository.save(category1);
        mockCategoryRepository.save(category2);
        mockCategoryRepository.save(category3);
        mockCategoryRepository.save(category4);
        mockCategoryRepository.save(category5);
        Mockito.when(mockCategoryRepository.findAllCategoryNames())
                .thenReturn(List.of(category1.getCategoryName().toString(),
                        category2.getCategoryName().toString(),
                        category3.getCategoryName().toString(),
                        category4.getCategoryName().toString(),
                        category5.getCategoryName().toString()));

        List<String> allCategoryNames = categoryServiceToTest.findAllCategories();

        Assertions.assertEquals(5, allCategoryNames.size());

        String category1 = allCategoryNames.get(0);
        String category2 = allCategoryNames.get(1);
        String category3 = allCategoryNames.get(2);
        String category4 = allCategoryNames.get(3);
        String category5 = allCategoryNames.get(4);

        Assertions.assertEquals("SALAD", category1);
        Assertions.assertEquals("PIZZA", category2);
        Assertions.assertEquals("DESSERT", category3);
        Assertions.assertEquals("MAIN_DISH", category4);
        Assertions.assertEquals("SUSHI", category5);
    }

}
