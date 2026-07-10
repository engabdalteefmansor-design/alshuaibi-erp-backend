package com.alshuaibi.erp.identity.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {

    private Long id;
    private String fullName;
    private String username;
    private String email;
    private String phone;
    private Boolean active;

    private Long roleId;
    private String roleCode;
    private String roleNameAr;

    private Long defaultBranchId;
    private String defaultBranchNameAr;
}