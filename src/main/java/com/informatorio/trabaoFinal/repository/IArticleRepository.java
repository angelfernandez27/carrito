package com.informatorio.trabaoFinal.repository;

import com.informatorio.trabaoFinal.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Set;

@Repository
public interface IArticleRepository extends JpaRepository<Article, Long> {

    @Modifying
    @Query(value = "UPDATE Article SET published=true WHERE ID=:id")
    public void markAsPublished(@Param("id") Long id);

    ///buscar article por una palabra
    //Query("from Article a where a.published =1 and (a.title like %:title% or a.description like %:title%)")
    @Query("from  Article ar inner join Author au on ar.author.id=au.id where ar.published=1 and " +
            "(ar.title like %:wordToSearch% or ar.description like %:wordToSearch% or " +
            "ar.content like %:wordToSearch% or au.fullname like %:wordToSearch%)")
    List<Article> getArticleByPublishedAndTitleOrDescriptionAndFullname(@Param("wordToSearch") String wordToSearch);

    @Query("from  Article ar inner join Author au on ar.author.id=au.id where ar.published=1 and " +
            "(ar.title like %:wordToSearch% or ar.description like %:wordToSearch% or " +
            "ar.content like %:wordToSearch% or au.fullname like %:wordToSearch%)")
    Set<Article> getArticleByPublishedAndTitleOrDescriptionAndFullnameSP(@Param("wordToSearch") String wordToSearch);

    //Set<Article> getArticleByTitleLike(@Param("title") String title);

    //Query para buscar article por un string mayor a 2 caracteres,
    // que haya sido publicado y por los campos title y description
    @Query("from Article a where a.published =1 and (a.title like %:title% or a.description like %:title%)")
    Page<Article> getArticleByTitleLikePage(@Param("title") Pageable pageable, String title);

    @Query("from Article a where a.published = 1")
    Set<Article> showArticlePublished();
}

