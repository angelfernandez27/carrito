package com.informatorio.trabaoFinal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.informatorio.trabaoFinal.model.*;
import com.informatorio.trabaoFinal.repository.IArticleRepository;
import com.informatorio.trabaoFinal.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    IArticleRepository iArticleRepository;

    @Autowired
    IArticleService iArticleService;

    //Crear author
    @PostMapping
    public ResponseEntity<?> createArticle(@RequestBody ArticleDTO articleDTO) {

        iArticleService.createArticle(articleDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Article creado");
    }

    //Marcar article como publicado
    @PatchMapping("/updateFinishes/{id}")
    public ResponseEntity<?> markAsPublished(@PathVariable("id") Long id){
        iArticleService.updateFinished(id);
        return ResponseEntity.status(HttpStatus.OK).body("El Article ha sido marcado como Publicado");
    }
    //Traer article por id
    @GetMapping("articleById/{id}")
    public ArticleDTO getArticle(@PathVariable Long id){

        return iArticleService.bringArticleById(id);
    }

    //Modificar article
    @PutMapping("/putArticle")
    public ResponseEntity<Article> modifyArticle(@RequestBody ArticleDTO newArticle) {
        Article article = iArticleService.updateArticle(newArticle);

        return new ResponseEntity<>(article, HttpStatus.OK);

    }
    //Borrar article
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable Long id) {
        iArticleService.deleteArticle(id);

        return ResponseEntity.status(HttpStatus.OK).body("Article removido");
    }

    @GetMapping("/allarticles/pages")
    public Page<ArticleDtoPagination> allArticlesPages(@RequestParam Integer pages, String title)
    {
        Pageable pageable = PageRequest.of(pages, 3);
        return iArticleService.getAllArticleLikePage(pageable, title);
    }


}
