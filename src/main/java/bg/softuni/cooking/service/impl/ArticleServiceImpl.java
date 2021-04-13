package bg.softuni.cooking.service.impl;

import bg.softuni.cooking.model.entity.Article;
import bg.softuni.cooking.model.service.ArticleServiceModel;
import bg.softuni.cooking.model.view.ArticleViewModel;
import bg.softuni.cooking.repository.ArticleRepository;
import bg.softuni.cooking.service.ArticleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;

    public ArticleServiceImpl(ArticleRepository articleRepository, ModelMapper modelMapper) {
        this.articleRepository = articleRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public void addArticle(ArticleServiceModel articleServiceModel) {
        articleRepository.save(modelMapper.map(articleServiceModel, Article.class));
    }

    @Override
    public List<ArticleViewModel> findAllArticles() {
        return articleRepository.findAllArticles().stream().map(article -> modelMapper.map(article, ArticleViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public ArticleViewModel findArticleById(Long id) {
        return articleRepository.findById(id).map(article -> modelMapper.map(article, ArticleViewModel.class)).orElse(null);
    }
}
