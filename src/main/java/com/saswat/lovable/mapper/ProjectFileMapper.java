package com.saswat.lovable.mapper;

import com.saswat.lovable.dto.project.FileNode;
import com.saswat.lovable.entity.ProjectFile;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectFileMapper {

    List<FileNode> toListOfFileNode(List<ProjectFile> projectFileList);
}
