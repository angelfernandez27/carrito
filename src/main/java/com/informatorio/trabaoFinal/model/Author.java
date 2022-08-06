package com.informatorio.trabaoFinal.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.time.LocalDate;


@Entity
@Data
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "firstname obligatorio")
    private String firstname;
    @NotBlank(message = "lastname Obligatorio")
    private String lastname;
    private String fullname;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate createdAT;
}
