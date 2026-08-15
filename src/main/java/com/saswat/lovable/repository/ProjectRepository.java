package com.saswat.lovable.repository;

import com.saswat.lovable.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("""
           SELECT p from Project p 
           WHERE p.deletedAt IS NULL
           AND P.owner.id = :userId
           ORDER BY p.updatedAt DESC
           """
    )
    List<Project> findAllAccessibleByUser(@Param("userId") Long userId);
}
