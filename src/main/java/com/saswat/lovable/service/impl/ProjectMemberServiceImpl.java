package com.saswat.lovable.service.impl;

import com.saswat.lovable.dto.member.InviteMemberRequest;
import com.saswat.lovable.dto.member.MemberResponse;
import com.saswat.lovable.entity.ProjectMember;
import com.saswat.lovable.service.ProjectMemberService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectMemberServiceImpl implements ProjectMemberService {

    @Override
    public List<ProjectMember> getProjectMembers(Long projectId, Long userId) {
        return List.of();
    }

    @Override
    public MemberResponse inviteMember(Long projectId, InviteMemberRequest request, Long userId) {
        return null;
    }

    @Override
    public MemberResponse updateMemberRole(
            Long projectId,
            Long memberId,
            InviteMemberRequest request,
            Long userId
    ) {
        return null;
    }

    @Override
    public MemberResponse deleteProjectMember(Long projectId, Long memberId, Long userId) {
        return null;
    }
}
