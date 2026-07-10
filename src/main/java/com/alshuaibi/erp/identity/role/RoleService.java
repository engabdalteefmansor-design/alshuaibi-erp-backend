package com.alshuaibi.erp.identity.role;

import com.alshuaibi.erp.common.response.PagedResponse;

public interface RoleService {

    PagedResponse<RoleResponse> getRoles(int page, int size, String sort);

    RoleResponse getRoleById(Long id);
}
