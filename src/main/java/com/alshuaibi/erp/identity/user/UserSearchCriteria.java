package com.alshuaibi.erp.identity.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSearchCriteria {

    private String username;
    private String fullName;
    private Boolean active;
}