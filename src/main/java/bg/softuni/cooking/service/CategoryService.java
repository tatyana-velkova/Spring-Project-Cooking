package bg.softuni.cooking.service;

import bg.softuni.cooking.model.entity.Category;
import bg.softuni.cooking.model.entity.CategoryEnum;

import java.util.List;

public interface CategoryService {
    void initCategories();

    List<String> findAllCategories();

    Category findByCategoryName(CategoryEnum categoryEnum);
}
