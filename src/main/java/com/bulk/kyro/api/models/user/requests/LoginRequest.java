package com.bulk.kyro.api.models.user.requests;

public record LoginRequest(
        String username,
        String password
) {
}
