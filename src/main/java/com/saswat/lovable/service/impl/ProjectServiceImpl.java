package com.saswat.lovable.service.impl;

import com.saswat.lovable.dto.project.ProjectRequest;
import com.saswat.lovable.dto.project.ProjectResponse;
import com.saswat.lovable.dto.project.ProjectSummaryResponse;
import com.saswat.lovable.entity.Project;
import com.saswat.lovable.entity.ProjectMember;
import com.saswat.lovable.entity.ProjectMemberId;
import com.saswat.lovable.entity.User;
import com.saswat.lovable.enums.ProjectRole;
import com.saswat.lovable.exception.BadRequestException;
import com.saswat.lovable.exception.ResourceNotFoundException;
import com.saswat.lovable.mapper.ProjectMapper;
import com.saswat.lovable.repository.ProjectMemberRepository;
import com.saswat.lovable.repository.ProjectRepository;
import com.saswat.lovable.repository.UserRepository;
import com.saswat.lovable.security.AuthUtil;
import com.saswat.lovable.service.ProjectService;
import com.saswat.lovable.service.ProjectTemplateService;
import com.saswat.lovable.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;
    private final ProjectMemberRepository projectMemberRepository;
    private final AuthUtil authUtil;
    private final SubscriptionService subscriptionService;
    private final ProjectTemplateService projectTemplateService;

    @Override
    public ProjectResponse createProject(ProjectRequest request) {

        if (!subscriptionService.canCreateNewProject()) {
            throw new BadRequestException("User cannot create a New project with current Plan, Upgrade plan now.");
        }

//        User owner = userRepository.findById(userId).orElseThrow(
//                () -> new ResourceNotFoundException("User", userId.toString())
//        );

        User owner = userRepository.getReferenceById(authUtil.getCurrentUserId());

        Project project = Project.builder()
                .name(request.name())
                .owner(owner)
                .isPublic(false)
                .build();
        project = projectRepository.save(project);

        ProjectMemberId projectMemberId = new ProjectMemberId(project.getId(), owner.getId());
        ProjectMember projectMember = ProjectMember.builder()
                .id(projectMemberId)
                .projectRole(ProjectRole.OWNER)
                .user(owner)
                .acceptedAt(Instant.now())
                .invitedAt(Instant.now())
                .project(project)
                .build();
        projectMemberRepository.save(projectMember);

        projectTemplateService.initializeProjectFromTemplate(project.getId());
        return projectMapper.toProjectResponse(project);

    }

    @Override
    public List<ProjectSummaryResponse> getUserProjects() {
        Long userId = authUtil.getCurrentUserId();
        var projectWithRoles = projectRepository.findAllAccessibleByUser(userId);
        return projectWithRoles.stream()
                .map(p -> projectMapper.toProjectSummaryResponse(p.getProject(), p.getRole()))
                .toList();
    }


    @Override
    @PreAuthorize("@security.canViewProject(#projectId)")
    public ProjectSummaryResponse getUserProjectById(Long projectId) {
        Long userId = authUtil.getCurrentUserId();
        var projectWithRole = projectRepository.findAccessibleProjectByIdWithRole(projectId, userId)
                .orElseThrow(() -> new BadRequestException("Project Not Found"));

        return projectMapper.toProjectSummaryResponse(projectWithRole.getProject(), projectWithRole.getRole());
    }

    @Override
    @PreAuthorize("@security.canEditProject(#projectId)")
    public ProjectResponse updateProject(Long id, ProjectRequest request) {
        Long userId = authUtil.getCurrentUserId();
        Project project = getAccessibleProjectById(id, userId);

        if (!project.getOwner().getId().equals(userId)) {
            throw new RuntimeException("You are not allowed to update this project ");
        }
        project.setName(request.name());
        projectRepository.save(project);

        return projectMapper.toProjectResponse(project);
    }

    @Override
    @PreAuthorize("@security.canDeleteProject(#projectId)")
    public void softDelete(Long id) {
        Long userId = authUtil.getCurrentUserId();
        Project project = getAccessibleProjectById(id, userId);

        if (!project.getOwner().getId().equals(userId)) {
            throw new RuntimeException("You are not allowed to delete this project");
        }

        project.setDeletedAt(Instant.now());
        projectRepository.save(project);
    }

    // INTERNAL FUNCTIONS
    public Project getAccessibleProjectById(Long projectId, Long userId) {
        return projectRepository.findAccessibleProjectsById(projectId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", projectId.toString()));

    }


}
