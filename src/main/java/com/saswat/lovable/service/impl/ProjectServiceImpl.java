package com.saswat.lovable.service.impl;

import com.saswat.lovable.dto.project.ProjectRequest;
import com.saswat.lovable.dto.project.ProjectResponse;
import com.saswat.lovable.dto.project.ProjectSummaryResponse;
import com.saswat.lovable.entity.Project;
import com.saswat.lovable.entity.User;
import com.saswat.lovable.mapper.ProjectMapper;
import com.saswat.lovable.repository.ProjectRepository;
import com.saswat.lovable.repository.UserRepository;
import com.saswat.lovable.service.ProjectService;
import lombok.RequiredArgsConstructor;
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

    @Override
    public ProjectResponse createProject(ProjectRequest request, Long userId) {

        User owner = userRepository.findById(userId).orElseThrow();

        Project project = Project.builder()
                .name(request.name())
                .owner(owner)
                .isPublic(false)
                .build();
        project = projectRepository.save(project);
        return projectMapper.toProjectResponse(project);

    }

    @Override
    public List<ProjectSummaryResponse> getUserProjects(Long userId) {
        var projects = projectRepository.findAllAccessibleByUser(userId);
        return projectMapper.toListOfProjectSummaryResponse(projects);
    }

    @Override
    public ProjectResponse getUserProjectById(Long id, Long userId) {
        Project project = getAccessibleProjectById(id, userId);
        return projectMapper.toProjectResponse(project);
    }

    @Override
    public ProjectResponse updateProject(Long id, ProjectRequest request, Long userId) {
        Project project = getAccessibleProjectById(id, userId);

        if (!project.getOwner().getId().equals(userId)) {
            throw new RuntimeException("You are not allowed to update this project ");
        }
        project.setName(request.name());
        projectRepository.save(project);

        return projectMapper.toProjectResponse(project);
    }

    @Override
    public void softDelete(Long id, Long userId) {
        Project project = getAccessibleProjectById(id, userId);

        if (!project.getOwner().getId().equals(userId)) {
            throw new RuntimeException("You are not allowed to delete this project");
        }

        project.setDeletedAt(Instant.now());
        projectRepository.save(project);
    }

    // INTERNAL FUNCTIONS
    public Project getAccessibleProjectById(Long projectId, Long userId) {
        return projectRepository.findAccessibleProjectsById(projectId, userId).orElseThrow();

    }


}
