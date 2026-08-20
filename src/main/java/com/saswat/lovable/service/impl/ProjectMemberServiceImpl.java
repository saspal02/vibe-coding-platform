package com.saswat.lovable.service.impl;

import com.saswat.lovable.dto.member.InviteMemberRequest;
import com.saswat.lovable.dto.member.MemberResponse;
import com.saswat.lovable.dto.member.UpdateMemberRoleRequest;
import com.saswat.lovable.entity.Project;
import com.saswat.lovable.entity.ProjectMember;
import com.saswat.lovable.entity.ProjectMemberId;
import com.saswat.lovable.entity.User;
import com.saswat.lovable.mapper.ProjectMemberMapper;
import com.saswat.lovable.repository.ProjectMemberRepository;
import com.saswat.lovable.repository.ProjectRepository;
import com.saswat.lovable.repository.UserRepository;
import com.saswat.lovable.service.ProjectMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectMemberServiceImpl implements ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMemberMapper projectMemberMapper;
    private final UserRepository userRepository;

    @Override
    public List<MemberResponse> getProjectMember(Long projectId, Long userId) {
        Project project = getAccessibleProjectById(projectId, userId);

        return projectMemberRepository.findByIdProjectId(projectId)
                        .stream()
                        .map(projectMemberMapper::fromProjectMember)
                        .toList();

    }

    @Override
    public MemberResponse inviteMember(Long projectId, InviteMemberRequest request, Long userId) {

        Project project = getAccessibleProjectById(projectId, userId);

        if (!project.getOwner().getId().equals(userId)) {
            throw new RuntimeException("You are not allowed to invite members to this project");
        }

        User invitee = userRepository.findByUsername(request.username()).orElseThrow();

        if (invitee.getId().equals(userId)) {
            throw new RuntimeException("Cannot invite yourself");
        }

        ProjectMemberId projectMemberId = new ProjectMemberId(projectId, invitee.getId());

        if (projectMemberRepository.existsById(projectMemberId)) {
            throw new RuntimeException("Cannot invite once again");
        }

        ProjectMember member = ProjectMember.builder()
                .id(projectMemberId)
                .project(project)
                .user(invitee)
                .projectRole(request.role())
                .build();

        projectMemberRepository.save(member);
        return projectMemberMapper.fromProjectMember(member);
    }

    @Override
    public MemberResponse updateMemberRole(Long projectId, Long memberId, UpdateMemberRoleRequest request, Long userId) {
        Project project = getAccessibleProjectById(projectId, userId);

        if (!project.getOwner().getId().equals(userId)) {
            throw new RuntimeException("Not allowed");
        }

        ProjectMemberId projectMemberId = new ProjectMemberId(projectId, memberId);
        ProjectMember projectMember = projectMemberRepository.findById(projectMemberId).orElseThrow();

        projectMember.setProjectRole(request.role());
        projectMemberRepository.save(projectMember);
        return projectMemberMapper.fromProjectMember(projectMember);
    }

    @Override
    public void removeProjectMember(Long projectId, Long memberId, Long userId) {

        Project project = getAccessibleProjectById(projectId, userId);

        if (!project.getOwner().getId().equals(userId)) {
            throw new RuntimeException("Not allowed");
        }

        ProjectMemberId projectMemberId = new ProjectMemberId(projectId, memberId);
        if (!projectMemberRepository.existsById(projectMemberId)) {
            throw new RuntimeException("Member not found in the project");
        }

        projectMemberRepository.deleteById(projectMemberId);

    }

    // INTERNAL FUNCTIONS
    public Project getAccessibleProjectById(Long projectId, Long userId) {
        return projectRepository.findAccessibleProjectsById(projectId, userId).orElseThrow();

    }

}
