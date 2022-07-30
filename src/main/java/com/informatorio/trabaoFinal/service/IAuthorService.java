package com.informatorio.trabaoFinal.service;

import com.informatorio.trabaoFinal.model.Author;
import com.informatorio.trabaoFinal.model.AuthorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;

import java.util.Set;

public interface IAuthorService {

    public void createAuthor(AuthorDTO authorDTO);
    public AuthorDTO mostrarUnActor(Long id);
    public void deleteAuthor(Long id);
    public Author updateAuthor(AuthorDTO authorDTO);
    public Collection<AuthorDTO> getAllAuthor();
    public Page<Author> getAllAuthor(Pageable pageable);
    public Set<AuthorDTO> getAuthorWithFullNameLike(String fullname);
    public Set<AuthorDTO> getAuthorWithCreatedAT(LocalDate fecha);
    public Page<AuthorDTO> getAllAuthorLikePage(Pageable pageable, LocalDate fecha);

}
