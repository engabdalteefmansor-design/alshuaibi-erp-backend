 package com.alshuaibi.erp.identity.user;

import com.alshuaibi.erp.common.response.PagedResponse;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserResponse createUser(CreateUserRequest request);

    PagedResponse<UserResponse> getUsers(UserSearchCriteria criteria, Pageable pageable);

    UserResponse getUserById(Long id);

    UserResponse updateUser(Long id, UpdateUserRequest request);

    void deactivateUser(Long id);

    void activateUser(Long id);
}