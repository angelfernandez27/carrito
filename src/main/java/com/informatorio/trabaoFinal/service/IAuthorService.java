package com.informatorio.trabaoFinal.service;

import com.informatorio.trabaoFinal.dto.SourceDTO;
import com.informatorio.trabaoFinal.model.Author;
import com.informatorio.trabaoFinal.dto.AuthorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Collection;

import java.util.Set;

public interface IAuthorService {

    public void createAuthor(AuthorDTO authorDTO);
    public AuthorDTO mostrarUnActor(Long id);
    public void deleteAuthor(Long id);
    public AuthorDTO updateAuthor(String firstname, String lastname,Long id);
    public Collection<AuthorDTO> getAllAuthor();
    public Page<AuthorDTO> getAllAuthor(Pageable pageable);
    public Set<AuthorDTO> getAuthorWithFullNameLike(String fullname);
    public Set<AuthorDTO> getAuthorWithCreatedAT(LocalDate fecha);
    public Page<AuthorDTO> getAllAuthorLikePage(Pageable pageable, LocalDate fecha);


}
