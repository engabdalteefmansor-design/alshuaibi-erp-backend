package com.alshuaibi.erp.common.constants;

public final class ApiPaths {

    private ApiPaths() {
    }

    public static final String API_V1 = "/api/v1";

    // Authentication
    public static final String AUTH = API_V1 + "/auth";

    // Identity
    public static final String USERS = API_V1 + "/users";
    public static final String ROLES = API_V1 + "/roles";
    public static final String PERMISSIONS = API_V1 + "/permissions";
    public static final String COMPANIES = API_V1 + "/companies";
    public static final String BRANCHES = API_V1 + "/branches";
}
