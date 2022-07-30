package com.informatorio.trabaoFinal.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;

@Getter
@Setter
public class AuthorDTO {

    private Long id;
    private String firstname;
    private String lastname;
    private String fullname;
    private LocalDate createdAT;

    public AuthorDTO(Long id, String firstname, String lastname, String fullname, LocalDate createdAT) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.fullname = fullname;
        this.createdAT = createdAT;
    }

    public AuthorDTO() {

    }

  //  public AuthorDTO(Long id, String firstname, String lastname, String fullname, LocalDate createdAT) {

    }


    //public AuthorDTO(Long id, String firstname, String lastname, String fullname, LocalDate createdAT) {



