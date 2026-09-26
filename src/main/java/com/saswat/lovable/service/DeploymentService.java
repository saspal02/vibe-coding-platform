package com.saswat.lovable.service;

import com.saswat.lovable.dto.deploy.DeployResponse;

public interface DeploymentService {

    DeployResponse deploy(Long projectId);
}
