package bg.softuni.cooking.model.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {
    private CategoryEnum categoryName;

    public Category() {
    }

    public Category(CategoryEnum categoryName) {
        this.categoryName = categoryName;
    }

    @Enumerated(EnumType.STRING)
    public CategoryEnum getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(CategoryEnum categoryName) {
        this.categoryName = categoryName;
    }
}
