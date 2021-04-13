package bg.softuni.cooking.repository;

import bg.softuni.cooking.model.entity.Article;
import org.hibernate.sql.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("SELECT a FROM Article a")
    List<Article> findAllArticles();

    Optional<Article> findById(Long id);
}
