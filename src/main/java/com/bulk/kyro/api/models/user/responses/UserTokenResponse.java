package com.bulk.kyro.api.models.user.responses;

public record UserTokenResponse(
        UserResponse user,
        String token
) {
}
