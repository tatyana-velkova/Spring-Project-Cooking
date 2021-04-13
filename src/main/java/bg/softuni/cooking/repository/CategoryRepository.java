package bg.softuni.cooking.repository;

import bg.softuni.cooking.model.entity.Category;
import bg.softuni.cooking.model.entity.CategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c.categoryName FROM Category c ORDER BY c.categoryName")
    List<String> findAllCategoryNames();

    Category findByCategoryName(CategoryEnum categoryEnum);


}
