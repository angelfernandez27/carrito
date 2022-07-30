package com.informatorio.trabaoFinal.service;

import com.fasterxml.jackson.databind.ObjectMapper;
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
        author.setFullname(author.getFirstname() + "  " + author
                .getLastname());
        author.setCreatedAT(LocalDate.now());
        iAuthorRepository.save(author);
    }

    //Mostrar un actor
    public AuthorDTO mostrarUnActor(Long id) {
        Optional<Author> author = iAuthorRepository.findById(id);
        if (author.isEmpty()) {

            throw new ResourceNotFoundException("Article no encontrado. id inexistente","id: ",id);
            //throw new Exceptions("Author no encontrada. id inexistente", HttpStatus.NOT_FOUND);
        }
        AuthorDTO authorDTO = null;
        authorDTO = mapper.convertValue(author, AuthorDTO.class);
        return authorDTO;
    }

    //B0rra un Author
    public void deleteAuthor(Long id) {
        Optional<Author> author = iAuthorRepository.findById(id);
        if (author.isEmpty()) {
            throw new ResourceNotFoundException("Author no encontrado.El proceso de ELIMINACIO ha sido cancelado. ","id: ",id);
            //throw new Exceptions("Author no encontrado.El proceso de ELIMINACIO ha sido cancelado", HttpStatus.NOT_FOUND);
        }
        iAuthorRepository.deleteById(id);
    }

    //Modificar un Author
    public Author updateAuthor(AuthorDTO authorDTO) {
        Optional<Author> author = iAuthorRepository.findById(authorDTO.getId());
        if (author.isEmpty()) {
            throw new NewsAppException("El Author que quiere actualizar no existe.  "
                    ,HttpStatus.BAD_REQUEST," La Actualiacion se canceló.");
           // throw new Exceptions("El Author que quiere" +
             //       " actualizar no existe. La Actualiacion se canceló", HttpStatus.NOT_FOUND);
        }
        Author author1 = mapper.convertValue(authorDTO, Author.class);

        return iAuthorRepository.save(author1);
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
    public Page<Author> getAllAuthor(Pageable pageable) {
        return iAuthorRepository.findAll(pageable);
    }

    //Buscar por un string en fullname
    public Set<AuthorDTO> getAuthorWithFullNameLike(String fullname) {
        Set<Author> authors = iAuthorRepository.getAuthorByFullNameLike(fullname);
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
            //throw new Exceptions("No existen Author creados posterior a la fecha dada", HttpStatus.NOT_FOUND);
        }
        return authorDTOS;
    }

    //Buscar authors creados despues de una fecha dada paginado
    public Page<AuthorDTO> getAllAuthorLikePage(Pageable pageable, LocalDate fecha){

        //  if ((title.length() > 2) || (title == "")) {
        Page<Author> page = iAuthorRepository.getAuthorByCreatedAtPage(pageable, fecha);
        int totalElements = (int) page.getTotalElements();

        return new PageImpl<AuthorDTO>(page.getContent().stream().map(author -> new AuthorDTO(
                author.getId(),
                author.getFirstname(),
                author.getLastname(),
                author.getFullname(),
                author.getCreatedAT()
        )).collect(Collectors.toList()), pageable, totalElements);
    }
}
