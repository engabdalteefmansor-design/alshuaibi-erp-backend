 CREATE TABLE permissions (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(100) NOT NULL UNIQUE,
    name_ar VARCHAR(200) NOT NULL,
    name_en VARCHAR(200) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE role_permissions (
    role_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    CONSTRAINT fk_role_permissions_role
        FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE,
    CONSTRAINT fk_role_permissions_permission
        FOREIGN KEY (permission_id) REFERENCES permissions(id) ON DELETE CASCADE
);

CREATE INDEX idx_role_permissions_role_id ON role_permissions(role_id);
CREATE INDEX idx_role_permissions_permission_id ON role_permissions(permission_id);

INSERT INTO permissions (code, name_ar, name_en, active)
VALUES
    ('USER_VIEW', 'عرض المستخدمين', 'View Users', TRUE),
    ('USER_CREATE', 'إنشاء مستخدم', 'Create User', TRUE),
    ('USER_UPDATE', 'تعديل مستخدم', 'Update User', TRUE),
    ('USER_ACTIVATE', 'تفعيل مستخدم', 'Activate User', TRUE),
    ('USER_DEACTIVATE', 'تعطيل مستخدم', 'Deactivate User', TRUE),
    ('ROLE_VIEW', 'عرض الأدوار', 'View Roles', TRUE),
    ('BRANCH_VIEW', 'عرض الفروع', 'View Branches', TRUE),
    ('BRANCH_CREATE', 'إنشاء فرع', 'Create Branch', TRUE),
    ('BRANCH_UPDATE', 'تعديل فرع', 'Update Branch', TRUE),
    ('COMPANY_VIEW', 'عرض الشركة', 'View Company', TRUE),
    ('COMPANY_UPDATE', 'تعديل الشركة', 'Update Company', TRUE);

INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r
JOIN permissions p ON p.code IN (
    'USER_VIEW',
    'USER_CREATE',
    'USER_UPDATE',
    'USER_ACTIVATE',
    'USER_DEACTIVATE',
    'ROLE_VIEW',
    'BRANCH_VIEW',
    'BRANCH_CREATE',
    'BRANCH_UPDATE',
    'COMPANY_VIEW',
    'COMPANY_UPDATE'
)
WHERE r.code = 'SUPER_ADMIN';