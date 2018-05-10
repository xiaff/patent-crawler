package me.xiaff.crawler.patent.repo;

import me.xiaff.crawler.patent.entity.Patent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PatentRepo extends JpaRepository<Patent, String> {

    @Query("SELECT count(p) FROM Patent p WHERE p.searchCode=:classCode AND YEAR(p.applicationDate)=:year")
    long countByClassCodeAndYear(@Param("classCode") String classCode, @Param("year") Integer year);
}
