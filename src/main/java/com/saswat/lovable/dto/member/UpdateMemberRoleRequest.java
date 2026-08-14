package com.saswat.lovable.dto.member;

import com.saswat.lovable.enums.ProjectRole;

public record UpdateMemberRoleRequest(
        ProjectRole role) {
}
