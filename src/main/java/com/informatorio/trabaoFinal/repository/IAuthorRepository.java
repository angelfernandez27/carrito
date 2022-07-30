package com.informatorio.trabaoFinal.repository;

import com.informatorio.trabaoFinal.model.Author;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Set;

@Repository
public interface IAuthorRepository extends JpaRepository<Author, Long> {

    //Buscar por un string en fullname
    @Query("from Author a where a.fullname like %:fullname%")
    Set<Author> getAuthorByFullNameLike(@Param("fullname")String fullname);

    @Query("from Author a where a.createdAT > :fecha")
    Set<Author> getAuthorByCreatedAt(@Param("fecha") LocalDate fecha);

    @Query("from Author a where a.createdAT > :fecha")
    Page<Author> getAuthorByCreatedAtPage(@Param("fecha") Pageable pageable, LocalDate fecha);


}
