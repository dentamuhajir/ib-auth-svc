INSERT INTO users (
    username,
    email,
    password_hash,
    full_name,
    status,
    password_changed_at
)
VALUES (
    'admin',
    'admin@ib.local',
    '$2a$10$hkutMwCvF0L6K79bLS8dbeqFIZzMmxQhyFFwFFjYDA5E/OXAcA/x2',
    'System Administrator',
    'ACTIVE',
    CURRENT_TIMESTAMP
)
ON CONFLICT (username) DO NOTHING;

INSERT INTO user_roles (
    user_id,
    role_id
)
SELECT
    u.id,
    r.id
FROM users u
JOIN roles r
    ON r.name = 'ROLE_ADMIN'
WHERE u.username = 'admin'
ON CONFLICT DO NOTHING;