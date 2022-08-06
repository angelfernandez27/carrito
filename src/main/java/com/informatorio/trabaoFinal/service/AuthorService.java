package com.informatorio.trabaoFinal.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.informatorio.trabaoFinal.dto.AuthorDTO;
import com.informatorio.trabaoFinal.exceptions.Exceptions;
import com.informatorio.trabaoFinal.exceptions.NewsAppException;
import com.informatorio.trabaoFinal.exceptions.ResourceNotFoundException;
import com.informatorio.trabaoFinal.model.*;
import com.informatorio.trabaoFinal.repository.IAuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class AuthorService implements IAuthorService {

    @Autowired
    IAuthorRepository iAuthorRepository;

    @Autowired
    ObjectMapper mapper;

    // Crear un Author
    public void createAuthor(AuthorDTO authorDTO) {

        Author author = mapper.convertValue(authorDTO, Author.class);
        author.setFullname(author.getFirstname() + " " + author
                .getLastname());
        author.setCreatedAT(LocalDate.now());
        iAuthorRepository.save(author);
    }

    //Mostrar un actor
    public AuthorDTO mostrarUnActor(Long id) {
        Optional<Author> author = iAuthorRepository.findById(id);
        if (author.isEmpty()) {

            throw new ResourceNotFoundException("Autor no encontrado. id inexistente","id: ",id);
        }
        AuthorDTO authorDTO = null;
        authorDTO = mapper.convertValue(author, AuthorDTO.class);
        return authorDTO;
    }

    //B0rra un Author
    public void deleteAuthor(Long id) {
        Optional<Author> author = iAuthorRepository.findById(id);
        if (author.isEmpty()) {
            throw new ResourceNotFoundException("Author no encontrado.El proceso de ELIMINACIO ha sido cancelado. ",
                    "id: ",id);
        }
        iAuthorRepository.deleteById(id);
    }

    //Modificar un Author
    public AuthorDTO updateAuthor(String firstname, String lastname,Long id) {
        Author author = iAuthorRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Author","id: ",id));
       
        author.setFirstname(firstname);
        author.setLastname(lastname);
        author.setFullname(author.getFirstname() + " " + author
                .getLastname());
        Author authorSave=iAuthorRepository.save(author);

        return mapper.convertValue(authorSave, AuthorDTO.class);
    }


    //Obtener todos los author
    public Collection<AuthorDTO> getAllAuthor() {
        List<Author> authors = iAuthorRepository.findAll();
        Set<AuthorDTO> authorDTOS = new HashSet<>();
        for (Author author : authors) {
            authorDTOS.add(mapper.convertValue(author, AuthorDTO.class));
        }
        return authorDTOS;
    }

    //Mostrar todos los author con paginacion
    public Page<AuthorDTO> getAllAuthor(Pageable pageable){
        Page<Author> page = iAuthorRepository.findAll(pageable);
        List<AuthorDTO> authorDTOS = page.getContent().stream().map(author -> mapper
                .convertValue(author, AuthorDTO.class)).toList();

        return new PageImpl<>(authorDTOS);
    }


    //Buscar por un string en fullname
    public Set<AuthorDTO> getAuthorWithFullNameLike(String fullname) {
        Set<Author> authors = iAuthorRepository.getAuthorByFullNameLike(fullname);
        if(authors.isEmpty()){
            throw new NewsAppException("Error.  "
                    ,HttpStatus.NOT_FOUND," BUSQUE FALLIDA. NINGUN REGISTRO EN FULLNAME COINCIDE CON LA BUSQUEDA LANZADA.");
        }
        Set<AuthorDTO> authorDTOS = new HashSet<>();
        for (Author author : authors) {
            authorDTOS.add(mapper.convertValue(author, AuthorDTO.class));
        }
        return authorDTOS;
    }

    //Buscar authors creados despues de una fecha dada
    public Set<AuthorDTO> getAuthorWithCreatedAT(LocalDate fecha) {
        Set<Author> authors = iAuthorRepository.getAuthorByCreatedAt(fecha);
        Set<AuthorDTO> authorDTOS = new HashSet<>();
        if (authors.size() > 0) {
            for (Author author : authors) {
                authorDTOS.add(mapper.convertValue(author, AuthorDTO.class));
            }

        } else {
            throw new NewsAppException("Error.  "
                    ,HttpStatus.NOT_FOUND," No existen Author creados posterior a la fecha dada.");
        }
        return authorDTOS;
    }

    //Buscar authors creados despues de una fecha dada paginado
    public Page<AuthorDTO> getAllAuthorLikePage(Pageable pageable, LocalDate fecha){


        Page<Author> page = iAuthorRepository.getAuthorByCreatedAtPage(pageable, fecha);
        if( page.isEmpty()){
            throw new NewsAppException("Error.  "
                    ,HttpStatus.NOT_FOUND," No existen Author creados posterior a la fecha dada.");
        }
        return new PageImpl<AuthorDTO>(page.getContent().stream().map(author -> new AuthorDTO(
                author.getId(),
                author.getFirstname(),
                author.getLastname(),
                author.getFullname(),
                author.getCreatedAT()
        )).collect(Collectors.toList()));
    }
}
