package com.bulk.kyro.api.models.user.responses;

import com.bulk.kyro.api.models.role.responses.RoleResponse;
import com.bulk.kyro.dl.entities.UserEntity;

public record UserResponse(
        Integer id,
        String username,
        RoleResponse role
) {
    public static UserResponse fromUser(UserEntity user){
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                RoleResponse.fromRole(user.getRole())
        );
    }
}
