package com.informatorio.trabaoFinal.controller;

import com.informatorio.trabaoFinal.dto.SourceDTO;
import com.informatorio.trabaoFinal.model.Author;
import com.informatorio.trabaoFinal.dto.AuthorDTO;
import com.informatorio.trabaoFinal.service.IAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;

import java.util.Set;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    IAuthorService iAuthorService;

    //Crear author
    @PostMapping
    public ResponseEntity<?> createAuthor(@Valid @RequestBody AuthorDTO authorDTO) {

        iAuthorService.createAuthor(authorDTO);
        return ResponseEntity.status(HttpStatus.OK).body("AUTHOR creado");
    }

    //Mostrar un author
    @GetMapping("/{id}")
    public AuthorDTO getAuthor(@PathVariable Long id){

        return iAuthorService.mostrarUnActor(id);
    }
    //Borrar author
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable Long id) {
        iAuthorService.deleteAuthor(id);
        return ResponseEntity.status(HttpStatus.OK).body("Author removido");

}
    //Modificar author
    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> updateSource(@RequestParam("firstname") String firstname,@RequestParam("lastname")String lastname, @PathVariable("id")Long id) {
        return new ResponseEntity<>(iAuthorService.updateAuthor(firstname, lastname, id), HttpStatus.OK);
    }
    //Obtener todos los author
    @GetMapping("/allauthors")
    public Collection<AuthorDTO> allauthor(){
        return iAuthorService.getAllAuthor();
    }

    //Mostrar author con paginacion
    @GetMapping("/allauthor/page")
    public Page<AuthorDTO> allAuthor(@RequestParam Integer page,@RequestParam Integer tam){
        Pageable pageable = PageRequest.of(page, tam);
        return iAuthorService.getAllAuthor(pageable);
    }



    //Buscar por una palabra
    @GetMapping("/fullname")
    public Set<AuthorDTO> getAuthorByFullName(@RequestParam String fullname){
        return iAuthorService.getAuthorWithFullNameLike(fullname);

    }
    //Buscar authors creados despues de una fecha dada
    @GetMapping("/byDate")
    public Set<AuthorDTO> getAuthorByCreatedAT(@RequestParam  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate fecha){
        return iAuthorService.getAuthorWithCreatedAT(fecha);

    }
    //Buscar authors creados despues de una fecha dada paginado
    @GetMapping("/byDate/page")
    public Page<AuthorDTO> getAuthorByCreatedATPage(@RequestParam Integer pages,@RequestParam Integer tam, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate fecha){

        Pageable pageable = PageRequest.of(pages,tam);
        return iAuthorService.getAllAuthorLikePage(pageable, fecha);

    }
}
