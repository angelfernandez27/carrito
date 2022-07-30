package com.informatorio.trabaoFinal.service;

import com.informatorio.trabaoFinal.model.AuthorDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AuthorServiceTest {
    @Autowired
    IAuthorService authorService;
    @Test
    public void testGetAuthorWithFullNameLike(){

        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setFirstname("Jose");
        authorDTO.setLastname("Greet");
        //authorDTO.setFullname("Jose Greet");


        authorService.createAuthor(authorDTO);

        Set<AuthorDTO> authorDTOS = authorService.getAuthorWithFullNameLike("ose");
         boolean busqueda = authorDTOS.size() > 0;

        assertTrue(busqueda);
    }

}