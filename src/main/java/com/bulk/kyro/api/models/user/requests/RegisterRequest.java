package com.bulk.kyro.api.models.user.requests;

import com.bulk.kyro.dl.entities.UserEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank @Size(max = 50) String username,
        @NotBlank String password
) {
    public UserEntity toUser(){
        return new UserEntity(
                username,
                password
        );
    }
}
