package com.saswat.lovable.security;

import com.saswat.lovable.enums.ProjectPermission;
import com.saswat.lovable.repository.ProjectMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("security")
@RequiredArgsConstructor
public class SecurityExpressions {

    private final ProjectMemberRepository projectMemberRepository;
    private final AuthUtil authUtil;

    private boolean hasPermission(final Long projectId, final ProjectPermission projectPermission) {
        final Long userId = authUtil.getCurrentUserId();
        return projectMemberRepository.findRoleByProjectIdAndUserId(projectId, userId)
                .map(role -> role.getPermissions().contains(projectPermission))
                .orElse(false);
    }

    public boolean canViewProject(final Long projectId) {
        return hasPermission(projectId, ProjectPermission.VIEW);
    }

    public boolean canEditProject(final Long projectId) {
        return hasPermission(projectId, ProjectPermission.EDIT);
    }

    public boolean canDeleteProject(final Long projectId) {
        return hasPermission(projectId, ProjectPermission.DELETE);
    }

    public boolean canViewMembers(final Long projectId) {
        return hasPermission(projectId, ProjectPermission.VIEW_MEMBERS);
    }

    public boolean canManageMembers(final Long projectId) {
        return hasPermission(projectId, ProjectPermission.MANAGE_MEMBERS);
    }
}
