package com.informatorio.trabaoFinal.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.informatorio.trabaoFinal.exceptions.NewsAppException;
import com.informatorio.trabaoFinal.exceptions.ResourceNotFoundException;
import com.informatorio.trabaoFinal.model.Source;
import com.informatorio.trabaoFinal.model.SourceDTO;
import com.informatorio.trabaoFinal.repository.ISourceRepository;
import com.informatorio.trabaoFinal.util.CodigoSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
        //codigoSource(code);

        Source source = mapper.convertValue(sourceDTO, Source.class);
        source.setCreateAt(LocalDate.now());
        source.setCode(codigoSource.crearcodigo(code));
        iSourceRepository.save(source);
    }

    private void codigoSource(String code) {
    }

    //Borrar Source
    public void deleteSource(Long id) {
        Optional<Source> source = iSourceRepository.findById(id);
        if (source.isEmpty()) {
            throw new ResourceNotFoundException("Source no encontrado. id inexistente","id: ",id);
           // throw new Exceptions("Source no encontrada", HttpStatus.NOT_FOUND);
        }
        iSourceRepository.deleteById(id);
    }
    //Modificar Source
    public Source updateSource(SourceDTO sourceDTO) {
            Optional<Source> source1 = iSourceRepository.findById(sourceDTO.getId());
          if (source1.isEmpty()) {
              throw new NewsAppException("El Source que quiere actualizar no existe.  "
                      ,HttpStatus.NOT_FOUND," La Actualiacion se canceló.");
               // throw new Exceptions("El Source que quiere" +
                 //       " actualizar no existe. La Actualiacion se canceló", HttpStatus.NOT_FOUND);
            }
            //sourceDTO.setId(sourceDTO.getId());
            Source source2 = mapper.convertValue(sourceDTO, Source.class);

            return iSourceRepository.save(source2);
        }

        public SourceDTO mostrarSource(Long id){

            SourceDTO sourceDTO=null;
            Optional<Source> source=iSourceRepository.findById(id);
            if(source.isEmpty()){

                throw new ResourceNotFoundException("Source no encontrada. Id inexistente. ","id: ",id);
               // throw new Exceptions("Source no encontrada. id inexistente", HttpStatus.NOT_FOUND);
            }
            sourceDTO = mapper.convertValue(source, SourceDTO.class);
            return sourceDTO;
    }
    //Mostrar todoss
    public Collection<SourceDTO> getAllSource(){
        List<Source> sourceList= iSourceRepository.findAll();
       List<SourceDTO> sourceDTOS= sourceList.stream()
               .map(source -> mapper.convertValue(source, SourceDTO.class))
               .collect(Collectors.toList());
        // Set<SourceDTO> sourceDTOSet = new HashSet<>();
        //for (Source source: sourceList){
         //   sourceDTOSet.add(mapper.convertValue(source, SourceDTO.class));
        //}
        return sourceDTOS;//sourceDTOSet;
    }

   //Mostrar todoss con paginacion
    public Page<Source> getAllSource(Pageable pageable) {

        return iSourceRepository.findAll(pageable);
    }

    //Buscar por una palabra dada
    public Set<SourceDTO> getSourceWithNameLike(String name){
        Set<Source> sources = iSourceRepository.getSourceByNameLike(name);
        Set<SourceDTO> sourceDTOSet = new HashSet<>();
        for (Source source: sources){
            sourceDTOSet.add(mapper.convertValue(source, SourceDTO.class));
        }
        return sourceDTOSet;
    }


}


