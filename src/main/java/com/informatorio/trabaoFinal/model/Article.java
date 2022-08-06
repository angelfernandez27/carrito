package com.informatorio.trabaoFinal.model;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "El titulo no puede ser nulo ni estar vacio!!!!!")
    private String title;
    @NotBlank(message = "La descrpcion no puede ser nulo ni estar vacio!!!!!")
    private String description;
    @NotBlank(message = "La Url de la imagen no puede ser nulo ni estar vacio!!!!!")
    private String urlToImage;
    @NotBlank(message = "La url  no puede ser nulo ni estar vacio!!!!!")
    private String url;
    private LocalDate publishedAt;
    private Boolean published;
    @NotBlank(message = "El contenido no puede ser nulo ni estar vacio!!!!!")
    @Lob
    private String content;


    @ManyToOne
    @JoinColumn(name="author_id", nullable = false)
    private Author author;

    @ManyToOne
    @JoinColumn(name= "source_id", nullable = false)
    private Source source;


    public Article(Long id, String title, String description, String urlToImage, String url, LocalDate publishedAt,
                   Boolean published, String content, Author author, Source source) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.urlToImage = urlToImage;
        this.url = url;
        this.publishedAt = publishedAt;
        this.published = published;
        this.content = content;
        this.author = author;
        this.source = source;
    }

    public Article() {
    }
}


