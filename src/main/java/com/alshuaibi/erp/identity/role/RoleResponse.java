 package com.alshuaibi.erp.identity.role;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoleResponse {

    private Long id;
    private String code;
    private String nameAr;
    private String nameEn;
    private Boolean active;
}