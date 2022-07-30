package com.informatorio.trabaoFinal.model;

import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String url;
    private LocalDate publishedAt;
    private boolean published;
    @Column(columnDefinition = "LONGBLOB")
    @Type(type = "org.hibernate.type.TextType")
    private String content;


    @ManyToOne
    @JoinColumn(name="author_id", nullable = false)
    private Author author;

    @ManyToOne
    @JoinColumn(name= "source_id", nullable = false)
    private Source source;
}
