package bg.softuni.cooking.service;

import bg.softuni.cooking.model.service.ArticleServiceModel;
import bg.softuni.cooking.model.view.ArticleViewModel;

import java.util.List;

public interface ArticleService {
    void addArticle(ArticleServiceModel articleServiceModel);

    List<ArticleViewModel> findAllArticles();

    ArticleViewModel findArticleById(Long id);
}
