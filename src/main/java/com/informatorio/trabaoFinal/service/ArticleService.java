package com.informatorio.trabaoFinal.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.informatorio.trabaoFinal.exceptions.NewsAppException;
import com.informatorio.trabaoFinal.exceptions.ResourceNotFoundException;
import com.informatorio.trabaoFinal.model.Article;
import com.informatorio.trabaoFinal.dto.ArticleDTO;
import com.informatorio.trabaoFinal.repository.IArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;


@Service
public class ArticleService implements IArticleService{

    @Autowired
    IArticleRepository iArticleRepository;
    @Autowired
    ObjectMapper mapper;

    // Crear un Article
    public void createArticle(ArticleDTO articleDTO) {
        Article article = mapper.convertValue(articleDTO, Article.class);
        article.setPublishedAt(LocalDate.now());
        article.setPublished(false);
        iArticleRepository.save(article);
    }
    //Poner article como publicado
    @Transactional


    public void updateFinished(Long id){
       Optional<Article> article = iArticleRepository.findById(id);
        if (article.isEmpty()) {
            throw new ResourceNotFoundException("Article no encontrado. id inexistente","id: ",id);
        }
        iArticleRepository.markAsPublished(id);
    }

        //Trae un article por id
    public ArticleDTO bringArticleById(Long id) {
        Optional<Article> article = iArticleRepository.findById(id);
        if (article.isEmpty()) {

            throw new ResourceNotFoundException("Article no encontrada. Id inexistente. ","id: ",id);
        }
        ArticleDTO articleDTO = null;
        articleDTO = mapper.convertValue(article, ArticleDTO.class);
        return articleDTO;
    }
    //Modificar un Article

    public ArticleDTO updateArticle(ArticleDTO articleDTO, Long id) {
        Article article = iArticleRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Author","id: ",id));
        article.setTitle(articleDTO.getTitle());
        article.setDescription(articleDTO.getDescription());
        article.setUrl(articleDTO.getUrl());
        article.setContent(articleDTO.getContent());
        Article articlesave = iArticleRepository.save(article);
    return mapper.convertValue(articlesave, ArticleDTO.class);
    }
    //Borrar un Article
    public void deleteArticle(Long id) {
        Optional<Article> article = iArticleRepository.findById(id);
        if (article.isEmpty()) {
            throw new ResourceNotFoundException("Article inexistente.El proceso de ELIMINACIO ha sido cancelado ","id: ",id);
        }
        iArticleRepository.deleteById(id);
    }
    //buscar article por un string mayor a 2 caracteres,
    // que haya sido publicado y por los campos title y description

    public Set<ArticleDTO> getAllArticleLike(String wordToSearch){

        Set<Article> articles = iArticleRepository.
                getArticleByPublishedAndTitleOrDescriptionAndFullnameSP(wordToSearch);

        if(articles.isEmpty()){
            throw new NewsAppException("Error.  "
                    ,HttpStatus.NOT_FOUND," BUSQUE FALLIDA. NINGUN REGISTRO  COINCIDE CON LA BUSQUEDA LANZADA.");
        }
        Set<ArticleDTO> articleDTOSet = new HashSet<>();
        for (Article article: articles){
            articleDTOSet.add(mapper.convertValue(article, ArticleDTO.class));
        }
        return articleDTOSet;
    }

    // buscar article por un string mayor a 2 caracteres,
    // que haya sido publicado y por los campos title y description paginado
   public Page<ArticleDTO> getAllArticleLikePage(Pageable pageable, String wordToSearch){

        List<Article> articlePage = iArticleRepository.
                getArticleByPublishedAndTitleOrDescriptionAndFullname( wordToSearch);

        List<ArticleDTO> articleDTOList= articlePage.stream().map(article -> mapper.convertValue(
                article, ArticleDTO.class)).toList();
        return new PageImpl<>(articleDTOList);
   }

    // Traer todos los articles publicados
    public Set<ArticleDTO> showAllPublished(){
        Set<Article> articles = iArticleRepository.showArticlePublished();
        if(articles.size()==0){
            throw new NewsAppException("Error.  "
                    ,HttpStatus.NOT_FOUND," No existen Author creados posterior a la fecha dada.");
        }

        Set<ArticleDTO> articleDTOS = new HashSet<>();
        for ( Article article : articles){
            articleDTOS.add(mapper.convertValue(article, ArticleDTO.class));
        }
        return articleDTOS;
    }


}
