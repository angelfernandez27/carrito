package com.informatorio.trabaoFinal.service;

import com.informatorio.trabaoFinal.model.Article;
import com.informatorio.trabaoFinal.model.Author;
import com.informatorio.trabaoFinal.model.Source;
import com.informatorio.trabaoFinal.repository.IArticleRepository;
import com.informatorio.trabaoFinal.repository.IAuthorRepository;
import com.informatorio.trabaoFinal.repository.ISourceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ArticleServiceTest {


    @Autowired
    private IArticleRepository articleRepository;

    @Autowired
    private IAuthorRepository authorRepository;

    @Autowired
    private ISourceRepository sourceRepository;


    @Test
    void createArticle() {


        Source source= new Source();
        source.setName("Source name");
        source.setCode("Source code");
        source.setCreateAt(LocalDate.now());
        Source sourceSave=sourceRepository.save(source);

        Author author=new Author();
        author.setFirstname("Author firstname");
        author.setLastname("Author lastname");
        author.setFullname("Author fullname");
        author.setCreatedAT(LocalDate.now());
        Author authorSave=authorRepository.save(author);


        Article article=new Article();
        article.setTitle("prueba");
        article.setDescription("probando article");
        article.setUrlToImage("url image prueba");
        article.setUrl("Url prueba");
        article.setPublished(true);
        article.setPublishedAt(LocalDate.now());
        article.setContent("content prueba");

        article.setAuthor(authorSave);
        article.setSource(sourceSave);

        Article articleSave=articleRepository.save(article);
        assertTrue(articleSave!=null);

    }

    @Test
    void getAllArticleLike() {
        List<Article>articleList=articleRepository.findAll();
        assertTrue(!articleList.isEmpty());
    }
}