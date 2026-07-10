package com.alshuaibi.erp.identity.user;

import com.alshuaibi.erp.common.base.BaseEntity;
import com.alshuaibi.erp.identity.branch.Branch;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_branch_access")
public class UserBranchAccess extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @Column(name = "can_view", nullable = false)
    private Boolean canView = true;

    @Column(name = "can_post_sales", nullable = false)
    private Boolean canPostSales = false;

    @Column(name = "can_post_purchases", nullable = false)
    private Boolean canPostPurchases = false;

    @Column(name = "can_manage_inventory", nullable = false)
    private Boolean canManageInventory = false;
}