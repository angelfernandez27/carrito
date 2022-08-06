package com.informatorio.trabaoFinal.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.informatorio.trabaoFinal.dto.SourceDTO;
import com.informatorio.trabaoFinal.exceptions.NewsAppException;
import com.informatorio.trabaoFinal.exceptions.ResourceNotFoundException;
import com.informatorio.trabaoFinal.model.Source;
import com.informatorio.trabaoFinal.repository.ISourceRepository;
import com.informatorio.trabaoFinal.util.CodigoSource;
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
public class SourceService implements ISourceService {

    @Autowired
    ISourceRepository iSourceRepository;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    CodigoSource codigoSource;

    // Crear un source
    public void createSource(SourceDTO sourceDTO) {

        String code = sourceDTO.getName();
        Source source = mapper.convertValue(sourceDTO, Source.class);
        source.setCreateAt(LocalDate.now());
        source.setCode(codigoSource.crearcodigo(code));
        iSourceRepository.save(source);
    }

   // private void codigoSource(String code) {
  //  }

    //Borrar Source
    public void deleteSource(Long id) {
        Optional<Source> source = iSourceRepository.findById(id);
        if (source.isEmpty()) {
            throw new ResourceNotFoundException("Source no encontrado. id inexistente","id: ",id);
        }
        iSourceRepository.deleteById(id);
    }


  //Modificar source
   public SourceDTO updateSource(String name, Long id){
       Source source=iSourceRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Source", "id", id));

       source.setName(name);
       source.setCode(codigoSource.crearcodigo(name));
       Source sourceSave=iSourceRepository.save(source);
       return mapper.convertValue(sourceSave, SourceDTO.class);
   }
   //Mostrar un source por id
        public SourceDTO mostrarSource(Long id){

            SourceDTO sourceDTO=null;
            Optional<Source> source=iSourceRepository.findById(id);
            if(source.isEmpty()){

                throw new ResourceNotFoundException("Source no encontrada. Id inexistente. ","id: ",id);
            }
            sourceDTO = mapper.convertValue(source, SourceDTO.class);
            return sourceDTO;
    }
    //Mostrar todoss
    public Collection<SourceDTO> getAllSource(){
        List<Source> sourceList= iSourceRepository.findAll();

            List<SourceDTO> sourceDTOS = sourceList.stream()
                    .map(source -> mapper.convertValue(source, SourceDTO.class))
                    .collect(Collectors.toList());
            // Set<SourceDTO> sourceDTOSet = new HashSet<>();
            //for (Source source: sourceList){
            //   sourceDTOSet.add(mapper.convertValue(source, SourceDTO.class));
            //}
            return sourceDTOS;//sourceDTOSet;

    }

   //Mostrar todoss con paginacion
    public Page<SourceDTO> getAllSource(Pageable pageable) {


        Page<Source> page = iSourceRepository.findAll(pageable);
       // List<SourceDTO> sourceDTOList = Collections.emptyList();
        List<SourceDTO> sourceDTOS =  page.getContent().stream().map(source -> mapper
                .convertValue(source, SourceDTO.class)).collect(Collectors.toList());

        //sourceDTOList = sourceDTOS;
        return new PageImpl<>(sourceDTOS);
    }

    //Buscar por una palabra dada
    public Set<SourceDTO> getSourceWithNameLike(String name){
        Set<Source> sources = iSourceRepository.getSourceByNameLike(name);

        if((sources.size()==0) || (name.length()==0)){
            throw new NewsAppException("Source", HttpStatus.NOT_FOUND,
                    "Ningún Source coincide con la búsqueda. Búsqueda cancelada.");
        }
        Set<SourceDTO> sourceDTOSet = new HashSet<>();
        for (Source source: sources){
            sourceDTOSet.add(mapper.convertValue(source, SourceDTO.class));
        }
        return sourceDTOSet;
    }


}


