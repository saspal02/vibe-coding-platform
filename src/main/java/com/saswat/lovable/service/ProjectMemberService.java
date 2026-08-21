package com.saswat.lovable.service;

import com.saswat.lovable.dto.member.InviteMemberRequest;
import com.saswat.lovable.dto.member.MemberResponse;
import com.saswat.lovable.dto.member.UpdateMemberRoleRequest;

import java.util.List;

public interface ProjectMemberService {

    List<MemberResponse> getProjectMember(Long projectId);

    MemberResponse inviteMember(Long projectId, InviteMemberRequest request);

    MemberResponse updateMemberRole(Long projectId, Long memberId, UpdateMemberRoleRequest request);

    void removeProjectMember(Long projectId, Long memberId);
}
