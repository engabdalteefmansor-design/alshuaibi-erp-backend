package com.alshuaibi.erp.identity.role;

import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public RoleResponse toResponse(Role role) {
        return RoleResponse.builder()
                .id(role.getId())
                .code(role.getCode() != null ? role.getCode().name() : null)
                .nameAr(role.getNameAr())
                .nameEn(role.getNameEn())
                .active(role.getActive())
                .build();
    }
}
