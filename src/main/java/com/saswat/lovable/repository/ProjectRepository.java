package com.saswat.lovable.repository;

import com.saswat.lovable.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("""
           SELECT p from Project p 
           WHERE p.deletedAt IS NULL
           ORDER BY p.updatedAt DESC
           """
    )
    List<Project> findAllAccessibleByUser(@Param("userId") Long userId);

    @Query("""
          SELECT p FROM Project p
          WHERE p.id = :projectId
              AND p.deletedAt IS NULL
    """)
    Optional<Project> findAccessibleProjectsById(@Param("projectId" ) Long projectId,
                                                 @Param("userId") Long userId);
}
