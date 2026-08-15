package com.saswat.lovable.entity;

import com.saswat.lovable.enums.ProjectRole;

import java.time.Instant;

public class ProjectMember {

    private ProjectMemberId id;
    private Project project;
    private User user;
    private ProjectRole projectRole;
    private Instant invitedAt;
    private Instant acceptedAt;

}
