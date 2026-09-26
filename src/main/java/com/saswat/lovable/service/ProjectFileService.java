package com.saswat.lovable.service;

import com.saswat.lovable.dto.project.FileContentResponse;
import com.saswat.lovable.dto.project.FileNode;
import com.saswat.lovable.dto.project.FileTreeResponse;

import java.util.List;

public interface ProjectFileService {

    FileTreeResponse getFileTree(Long projectId);

    FileContentResponse getFileContent(Long projectId, String path);

    void saveFile(Long projectId, String filePath, String fileContent);
}
