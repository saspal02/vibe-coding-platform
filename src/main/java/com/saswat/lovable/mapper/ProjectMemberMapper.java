package com.saswat.lovable.mapper;

import com.saswat.lovable.dto.member.MemberResponse;
import com.saswat.lovable.entity.ProjectMember;
import com.saswat.lovable.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectMemberMapper {

    @Mapping(target = "userId", source = "id")
    @Mapping(target = "projectRole", constant = "OWNER")
    MemberResponse fromProjectMember(User owner);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "name", source = "user.name")
    MemberResponse fromProjectMember(ProjectMember projectMember);
}
