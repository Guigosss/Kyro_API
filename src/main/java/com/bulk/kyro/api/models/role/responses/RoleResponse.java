package com.bulk.kyro.api.models.role.responses;

import com.bulk.kyro.dl.entities.RoleEntity;

public record RoleResponse(
        Integer id,
        String name
) {
    public static RoleResponse fromRole(RoleEntity role){
        return new RoleResponse(
                role.getId(),
                role.getName()
        );
    }
}
