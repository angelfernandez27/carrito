package com.informatorio.trabaoFinal.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SourceDTO {

    private Long id;
    private String name;
    private String code;
    private LocalDate createAt;

    public SourceDTO(Long id, String name, String code, LocalDate createAt) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.createAt = createAt;
    }

    public SourceDTO() {
    }
}
