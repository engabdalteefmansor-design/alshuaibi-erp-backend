 package com.alshuaibi.erp.common.config;

import com.alshuaibi.erp.identity.branch.Branch;
import com.alshuaibi.erp.identity.branch.BranchRepository;
import com.alshuaibi.erp.identity.company.Company;
import com.alshuaibi.erp.identity.company.CompanyRepository;
import com.alshuaibi.erp.identity.permission.Permission;
import com.alshuaibi.erp.identity.permission.PermissionCode;
import com.alshuaibi.erp.identity.permission.PermissionRepository;
import com.alshuaibi.erp.identity.role.Role;
import com.alshuaibi.erp.identity.role.RoleRepository;
import com.alshuaibi.erp.identity.role.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final CompanyRepository companyRepository;
    private final BranchRepository branchRepository;
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public void run(String... args) {
        Company company = seedCompany();
        seedMainBranch(company);
        seedPermissions();
        seedRolePermissions();
    }

    private Company seedCompany() {
        if (companyRepository.count() > 0) {
            return companyRepository.findAll().getFirst();
        }

        Company company = new Company();
        company.setNameAr("شركة الشعيبي");
        company.setNameEn("Alshuaibi Trading");
        company.setTaxNumber("0000000000");
        company.setPhone("000000000");
        company.setEmail("info@alshuaibi.local");
        company.setAddress("اليمن");
        company.setActive(true);

        return companyRepository.save(company);
    }

    private void seedMainBranch(Company company) {
        if (branchRepository.count() > 0) {
            return;
        }

        Branch branch = new Branch();
        branch.setCompany(company);
        branch.setCode("MAIN");
        branch.setNameAr("الفرع الرئيسي");
        branch.setNameEn("Main Branch");
        branch.setPhone("000000000");
        branch.setAddress("المركز الرئيسي");
        branch.setManagerName("System");
        branch.setActive(true);

        branchRepository.save(branch);
    }

    private void seedPermissions() {
        seedPermission(PermissionCode.USER_VIEW, "عرض المستخدمين", "View Users");
        seedPermission(PermissionCode.USER_CREATE, "إنشاء مستخدم", "Create User");
        seedPermission(PermissionCode.USER_UPDATE, "تحديث مستخدم", "Update User");
        seedPermission(PermissionCode.USER_ACTIVATE, "تفعيل مستخدم", "Activate User");
        seedPermission(PermissionCode.USER_DEACTIVATE, "تعطيل مستخدم", "Deactivate User");

        seedPermission(PermissionCode.ROLE_VIEW, "عرض الأدوار", "View Roles");

        seedPermission(PermissionCode.BRANCH_VIEW, "عرض الفروع", "View Branches");
        seedPermission(PermissionCode.BRANCH_CREATE, "إنشاء فرع", "Create Branch");
        seedPermission(PermissionCode.BRANCH_UPDATE, "تحديث فرع", "Update Branch");

        seedPermission(PermissionCode.COMPANY_VIEW, "عرض الشركة", "View Company");
        seedPermission(PermissionCode.COMPANY_UPDATE, "تحديث الشركة", "Update Company");
    }

    private void seedPermission(PermissionCode code, String nameAr, String nameEn) {
        if (permissionRepository.existsByCode(code)) {
            return;
        }

        Permission permission = new Permission();
        permission.setCode(code);
        permission.setNameAr(nameAr);
        permission.setNameEn(nameEn);
        permission.setActive(true);

        permissionRepository.save(permission);
    }

    private void seedRolePermissions() {
        assignPermissions(RoleType.SUPER_ADMIN, EnumSet.allOf(PermissionCode.class));

        assignPermissions(RoleType.COMPANY_ADMIN, Set.of(
                PermissionCode.USER_VIEW,
                PermissionCode.USER_CREATE,
                PermissionCode.USER_UPDATE,
                PermissionCode.USER_ACTIVATE,
                PermissionCode.USER_DEACTIVATE,
                PermissionCode.ROLE_VIEW,
                PermissionCode.BRANCH_VIEW,
                PermissionCode.BRANCH_CREATE,
                PermissionCode.BRANCH_UPDATE,
                PermissionCode.COMPANY_VIEW,
                PermissionCode.COMPANY_UPDATE
        ));

        assignPermissions(RoleType.BRANCH_MANAGER, Set.of(
                PermissionCode.USER_VIEW,
                PermissionCode.BRANCH_VIEW,
                PermissionCode.COMPANY_VIEW
        ));

        assignPermissions(RoleType.ACCOUNTANT, Set.of(
                PermissionCode.COMPANY_VIEW,
                PermissionCode.BRANCH_VIEW
        ));

        assignPermissions(RoleType.CASHIER, Set.of(
                PermissionCode.BRANCH_VIEW
        ));

        assignPermissions(RoleType.STORE_KEEPER, Set.of(
                PermissionCode.BRANCH_VIEW
        ));

        assignPermissions(RoleType.SALES_REP, Set.of(
                PermissionCode.BRANCH_VIEW
        ));
    }

    private void assignPermissions(RoleType roleType, Set<PermissionCode> permissionCodes) {
        Role role = roleRepository.findByCode(roleType).orElse(null);
        if (role == null) {
            return;
        }

        Set<Permission> permissions = permissionCodes.stream()
                .map(code -> permissionRepository.findByCode(code).orElse(null))
                .filter(java.util.Objects::nonNull)
                .collect(java.util.stream.Collectors.toSet());

        role.getPermissions().clear();
        role.getPermissions().addAll(permissions);
        roleRepository.save(role);
    }
}