package com.saswat.lovable.mapper;

import com.saswat.lovable.dto.auth.SignupRequest;
import com.saswat.lovable.dto.auth.UserProfileResponse;
import com.saswat.lovable.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(SignupRequest signupRequest);

    UserProfileResponse toUserProfileResponse(User user);
}
