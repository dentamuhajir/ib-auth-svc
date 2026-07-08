CREATE TABLE users
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,

    password_hash VARCHAR(255) NOT NULL,

    full_name VARCHAR(150) NOT NULL,

    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',

    failed_login_attempt INTEGER NOT NULL DEFAULT 0,
    locked_until TIMESTAMP,

    last_login_at TIMESTAMP,
    password_changed_at TIMESTAMP,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    version BIGINT NOT NULL DEFAULT 0
);

CREATE INDEX idx_users_username
    ON users(username);

CREATE INDEX idx_users_email
    ON users(email);