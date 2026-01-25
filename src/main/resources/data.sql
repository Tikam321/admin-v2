INSERT INTO admin_user (user_id, user_name, email, admin_type, privilege_value, status, created_at, updated_at)
VALUES (1001, 'Super Admin', 'superadmin@example.com', 'SUPER_ADMIN', 'ALL_ACCESS', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO admin_user (user_id, user_name, email, admin_type, privilege_value, status, created_at, updated_at)
VALUES (1002, 'Admin User', 'admin@example.com', 'ADMIN', 'WRITE_ACCESS', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO admin_user (user_id, user_name, email, admin_type, privilege_value, status, created_at, updated_at)
VALUES (1003, 'Viewer User', 'viewer@example.com', 'VIEWER', 'READ_ONLY', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO applink (app_name, app_url, description, status, created_at, updated_at)
VALUES ('Google', 'https://www.google.com', 'Search Engine', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO applink (app_name, app_url, description, status, created_at, updated_at)
VALUES ('GitHub', 'https://github.com', 'Code Hosting Platform', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO applink (app_name, app_url, description, status, created_at, updated_at)
VALUES ('Stack Overflow', 'https://stackoverflow.com', 'Developer Community', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
