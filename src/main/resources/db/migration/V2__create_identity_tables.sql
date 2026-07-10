CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(100) NOT NULL UNIQUE,
    name_ar VARCHAR(200) NOT NULL,
    name_en VARCHAR(200),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);

CREATE TABLE permissions (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(100) NOT NULL UNIQUE,
    name_ar VARCHAR(200) NOT NULL,
    name_en VARCHAR(200),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    full_name VARCHAR(200) NOT NULL,
    username VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    email VARCHAR(150),
    phone VARCHAR(50),
    role_id BIGINT NOT NULL,
    default_branch_id BIGINT,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT fk_users_role
        FOREIGN KEY (role_id) REFERENCES roles(id),
    CONSTRAINT fk_users_default_branch
        FOREIGN KEY (default_branch_id) REFERENCES branches(id)
);

CREATE TABLE user_branch_access (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    branch_id BIGINT NOT NULL,
    can_view BOOLEAN NOT NULL DEFAULT TRUE,
    can_post_sales BOOLEAN NOT NULL DEFAULT FALSE,
    can_post_purchases BOOLEAN NOT NULL DEFAULT FALSE,
    can_manage_inventory BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_user_branch_access_user
        FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_user_branch_access_branch
        FOREIGN KEY (branch_id) REFERENCES branches(id),
    CONSTRAINT uk_user_branch_access UNIQUE (user_id, branch_id)
);