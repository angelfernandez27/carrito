package com.informatorio.trabaoFinal.service;

import com.informatorio.trabaoFinal.model.Article;
import com.informatorio.trabaoFinal.model.ArticleDTO;
import com.informatorio.trabaoFinal.model.ArticleDtoPagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IArticleService {
    public void createArticle(ArticleDTO articleDTO);
    public void updateFinished(Long id);
    public void deleteArticle(Long id);
    public Page<ArticleDtoPagination> getAllArticleLikePage(Pageable pageable, String title);
    public ArticleDTO bringArticleById(Long id);
    public Article updateArticle(ArticleDTO articleDTO);
}
