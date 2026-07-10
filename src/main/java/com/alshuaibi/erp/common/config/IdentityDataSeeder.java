package com.alshuaibi.erp.common.config;

import com.alshuaibi.erp.identity.branch.Branch;
import com.alshuaibi.erp.identity.branch.BranchRepository;
import com.alshuaibi.erp.identity.permission.Permission;
import com.alshuaibi.erp.identity.permission.PermissionCode;
import com.alshuaibi.erp.identity.permission.PermissionRepository;
import com.alshuaibi.erp.identity.role.Role;
import com.alshuaibi.erp.identity.role.RoleRepository;
import com.alshuaibi.erp.identity.role.RoleType;
import com.alshuaibi.erp.identity.user.User;
import com.alshuaibi.erp.identity.user.UserBranchAccess;
import com.alshuaibi.erp.identity.user.UserBranchAccessRepository;
import com.alshuaibi.erp.identity.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IdentityDataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;
    private final UserBranchAccessRepository userBranchAccessRepository;
    private final BranchRepository branchRepository;

    @Override
    public void run(String... args) {
        seedRoles();
        seedPermissions();
        seedSuperAdmin();
    }

    private void seedRoles() {
        if (roleRepository.count() > 0) {
            return;
        }

        createRole(RoleType.SUPER_ADMIN, "مدير النظام الأعلى", "Super Admin");
        createRole(RoleType.COMPANY_ADMIN, "مدير الشركة", "Company Admin");
        createRole(RoleType.BRANCH_MANAGER, "مدير الفرع", "Branch Manager");
        createRole(RoleType.ACCOUNTANT, "محاسب", "Accountant");
        createRole(RoleType.CASHIER, "كاشير", "Cashier");
        createRole(RoleType.STORE_KEEPER, "أمين مستودع", "Store Keeper");
        createRole(RoleType.SALES_REP, "مندوب مبيعات", "Sales Representative");
    }

    private void createRole(RoleType code, String nameAr, String nameEn) {
        Role role = new Role();
        role.setCode(code);
        role.setNameAr(nameAr);
        role.setNameEn(nameEn);
        role.setActive(true);
        roleRepository.save(role);
    }

    private void seedPermissions() {
        if (permissionRepository.count() > 0) {
            return;
        }

        for (PermissionCode code : PermissionCode.values()) {
            Permission permission = new Permission();
            permission.setCode(code);
            permission.setNameAr(code.name());
            permission.setNameEn(code.name());
            permission.setActive(true);
            permissionRepository.save(permission);
        }
    }

    private void seedSuperAdmin() {
        if (userRepository.existsByUsername("superadmin")) {
            return;
        }

        Role superAdminRole = roleRepository.findByCode(RoleType.SUPER_ADMIN)
                .orElseThrow(() -> new IllegalStateException("SUPER_ADMIN role not found"));

        Branch mainBranch = branchRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No branch found"));

        User user = new User();
        user.setFullName("System Super Admin");
        user.setUsername("superadmin");
        user.setPasswordHash("admin123");
        user.setEmail("superadmin@alshuaibi.local");
        user.setPhone("000000000");
        user.setRole(superAdminRole);
        user.setDefaultBranch(mainBranch);
        user.setActive(true);

        User savedUser = userRepository.save(user);

        UserBranchAccess access = new UserBranchAccess();
        access.setUser(savedUser);
        access.setBranch(mainBranch);
        access.setCanView(true);
        access.setCanPostSales(true);
        access.setCanPostPurchases(true);
        access.setCanManageInventory(true);

        userBranchAccessRepository.save(access);
    }
}