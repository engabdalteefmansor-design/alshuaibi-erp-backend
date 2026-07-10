package com.alshuaibi.erp.identity.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {

    @NotBlank
    private String fullName;

    private String email;
    private String phone;

    @NotNull
    private Long roleId;

    private Long defaultBranchId;

    @NotNull
    private Boolean active;
}