package com.saswat.lovable.repository;

import com.saswat.lovable.entity.ProjectMember;
import com.saswat.lovable.entity.ProjectMemberId;
import com.saswat.lovable.enums.ProjectRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, ProjectMemberId> {

    List<ProjectMember> findByIdProjectId(Long projectId);

    @Query("""
            SELECT pm.projectRole FROM ProjectMember pm
            WHERE pm.id.projectId = :projectId AND pm.id.userId = :userId
           """)
    Optional<ProjectRole> findRoleByProjectIdAndUserId(@Param("projectId") Long projectId,
                                                       @Param("userId") Long userId);


    @Query("""
        SELECT COUNT(pm) FROM ProjectMember pm
        WHERE pm.id.userId = :userId AND pm.projectRole = 'OWNER'
        """)
    int countProjectOwnedByUser(@Param("userId") Long userId);
}
