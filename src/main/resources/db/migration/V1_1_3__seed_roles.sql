INSERT INTO roles (name, description)
VALUES
    ('ROLE_ADMIN', 'System Administrator'),
    ('ROLE_CUSTOMER', 'Internet Banking Customer'),
    ('ROLE_AUDITOR', 'Audit User'),
    ('ROLE_CS', 'Customer Service')
ON CONFLICT (name) DO NOTHING;