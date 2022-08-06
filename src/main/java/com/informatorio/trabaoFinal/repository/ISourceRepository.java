package com.informatorio.trabaoFinal.repository;

import com.informatorio.trabaoFinal.model.Source;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ISourceRepository  extends JpaRepository<Source, Long> {
    @Query("from Source s where s.name like %:name%")
    Set<Source> getSourceByNameLike(@Param("name") String name);
}
   /* @Modifying
    @Query("update Source s set s.name:name, s.code:code where s.id:ids")
    Set<Source> updateSource(@Param("name")String name, @Param("code") String code, @Param("ids") Long ids);
}*/
