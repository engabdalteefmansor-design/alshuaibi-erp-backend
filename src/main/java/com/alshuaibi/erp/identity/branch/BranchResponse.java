package com.alshuaibi.erp.identity.branch;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BranchResponse {
    private Long id;
    private String code;
    private String nameAr;
    private String nameEn;
    private String phone;
    private String address;
    private Boolean active;
}