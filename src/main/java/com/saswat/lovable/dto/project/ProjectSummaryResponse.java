package com.saswat.lovable.dto.project;

import com.saswat.lovable.enums.ProjectRole;

public record ProjectSummaryResponse(
        Long id,
        String name,
        ProjectRole role

) {
}
