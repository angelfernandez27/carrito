package com.informatorio.trabaoFinal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.informatorio.trabaoFinal.dto.ArticleDTO;
import com.informatorio.trabaoFinal.repository.IArticleRepository;
import com.informatorio.trabaoFinal.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;


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
    public ResponseEntity<?> createArticle(@Valid @RequestBody ArticleDTO articleDTO) {

        iArticleService.createArticle(articleDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Article creado");
    }

    //Marcar article como publicado
    @PatchMapping("/updateFinishes/{id}")
    public ResponseEntity<?> markAsPublished(@PathVariable("id") Long id){
        iArticleService.updateFinished(id);
        return ResponseEntity.status(HttpStatus.OK).body("El Article ha sido marcado como Publicado");
    }
    @GetMapping("/updatePublished")
    public Set<ArticleDTO> allPublished(){
        return iArticleService.showAllPublished();
    }
    //Traer article por id
    @GetMapping("articleById/{id}")
    public ArticleDTO getArticle(@PathVariable Long id){

        return iArticleService.bringArticleById(id);
    }

    //Modificar article
    @PutMapping("/{id}")
    public ResponseEntity<ArticleDTO> modifyArticle(@PathVariable Long id, @RequestBody ArticleDTO articleDTO) {
        //Article article = iArticleService.updateArticle(articleDTO);

        return new ResponseEntity<>(iArticleService.updateArticle(articleDTO, id), HttpStatus.OK);

    }
    //Borrar article
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable Long id) {
        iArticleService.deleteArticle(id);

        return ResponseEntity.status(HttpStatus.OK).body("Article removido");
    }


    @GetMapping("/allarticles")
    Set<ArticleDTO> allArticles(@RequestParam String wordToSearch){
        return iArticleService.getAllArticleLike(wordToSearch);
    }
    // buscar article por un string mayor a 2 caracteres,
    // que haya sido publicado y por los campos title y description paginado
   @GetMapping("/allarticles/page")
    Page<ArticleDTO>  allArticlePage(@RequestParam Integer pages, String wordToSearch){
        Pageable pageable = PageRequest.of(pages, 3);
        return iArticleService.getAllArticleLikePage(pageable, wordToSearch);
   }


}
