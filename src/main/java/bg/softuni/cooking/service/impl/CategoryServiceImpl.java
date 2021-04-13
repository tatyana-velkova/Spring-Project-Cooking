package bg.softuni.cooking.service.impl;

import bg.softuni.cooking.model.entity.Category;
import bg.softuni.cooking.model.entity.CategoryEnum;
import bg.softuni.cooking.repository.CategoryRepository;
import bg.softuni.cooking.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void initCategories() {
        if (categoryRepository.count() == 0){

            for (CategoryEnum categoryEnum : CategoryEnum.values()) {
                Category category = new Category(CategoryEnum.valueOf(categoryEnum.name()));
                categoryRepository.save(category);

            }
        }
    }

    @Override
    public List<String> findAllCategories() {
        return categoryRepository.findAllCategoryNames();
    }

    @Override
    public Category findByCategoryName(CategoryEnum categoryEnum) {
        return categoryRepository.findByCategoryName(categoryEnum);
    }
}
