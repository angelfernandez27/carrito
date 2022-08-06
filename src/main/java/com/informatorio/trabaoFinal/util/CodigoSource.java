package com.informatorio.trabaoFinal.util;

import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class CodigoSource {

    public String crearcodigo(String code){

        //String name = code.getName();
        String name = code.trim().toLowerCase(Locale.ROOT).replace(" ","-");
        //sourceDTO.setCode(codigo);
        return name;

    }
}
