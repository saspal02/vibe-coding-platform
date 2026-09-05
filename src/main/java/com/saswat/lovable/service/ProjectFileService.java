package com.saswat.lovable.service;

import com.saswat.lovable.dto.project.FileContentResponse;
import com.saswat.lovable.dto.project.FileNode;

import java.util.List;

public interface ProjectFileService {

    List<FileNode> getFileTree(Long projectId, Long userId);

    FileContentResponse getFileContent(Long projectId, String path, Long userId);

    void saveFile(Long projectId, String filePath, String fileContent);
}
