package com.alshuaibi.erp.identity.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserBranchAccessRepository extends JpaRepository<UserBranchAccess, Long> {
    List<UserBranchAccess> findByUserId(Long userId);
}