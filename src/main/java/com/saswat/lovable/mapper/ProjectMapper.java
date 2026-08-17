package com.saswat.lovable.mapper;

import com.saswat.lovable.dto.project.ProjectResponse;
import com.saswat.lovable.dto.project.ProjectSummaryResponse;
import com.saswat.lovable.entity.Project;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    ProjectResponse toProjectResponse(Project project);

    List<ProjectSummaryResponse> toListOfProjectSummaryResponse(List<Project> projects);
}
