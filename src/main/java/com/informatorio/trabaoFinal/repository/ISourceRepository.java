package com.informatorio.trabaoFinal.repository;

import com.informatorio.trabaoFinal.model.Source;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ISourceRepository  extends JpaRepository<Source, Long> {
    @Query("from Source s where s.name like %:name%")
    Set<Source> getSourceByNameLike(@Param("name")String name);

}
