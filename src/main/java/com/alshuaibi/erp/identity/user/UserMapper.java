package com.alshuaibi.erp.identity.user;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .active(user.getActive())
                .roleId(user.getRole() != null ? user.getRole().getId() : null)
                .roleCode(user.getRole() != null ? user.getRole().getCode().name() : null)
                .roleNameAr(user.getRole() != null ? user.getRole().getNameAr() : null)
                .defaultBranchId(user.getDefaultBranch() != null ? user.getDefaultBranch().getId() : null)
                .defaultBranchNameAr(user.getDefaultBranch() != null ? user.getDefaultBranch().getNameAr() : null)
                .build();
    }
}