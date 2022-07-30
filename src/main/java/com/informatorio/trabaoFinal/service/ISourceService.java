package com.informatorio.trabaoFinal.service;

import com.informatorio.trabaoFinal.model.Source;
import com.informatorio.trabaoFinal.model.SourceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.Set;

public interface ISourceService {
    public void createSource(SourceDTO sourceDTO);
    public void deleteSource(Long id);
    public Source updateSource(SourceDTO sourceDTO);
    public SourceDTO mostrarSource(Long id);
    public Collection<SourceDTO> getAllSource();
    public Page<Source> getAllSource(Pageable pageable);


    public Set<SourceDTO> getSourceWithNameLike(String name);
}
