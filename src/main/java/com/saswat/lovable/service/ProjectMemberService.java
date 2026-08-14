package com.saswat.lovable.service;

import com.saswat.lovable.dto.member.InviteMemberRequest;
import com.saswat.lovable.dto.member.MemberResponse;
import com.saswat.lovable.entity.ProjectMember;

import java.util.List;

public interface ProjectMemberService {

    List<ProjectMember> getProjectMembers(Long projectId, Long userId);

    MemberResponse inviteMember(Long projectId, InviteMemberRequest request, Long userId);

    MemberResponse updateMemberRole(Long projectId, Long memberId, InviteMemberRequest request, Long userId);

    MemberResponse deleteProjectMember(Long projectId, Long memberId, Long userId);
}
