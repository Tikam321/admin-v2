INSERT INTO admin_user (user_id, user_name, email, admin_type, privilege_value, status, created_at, updated_at)
VALUES (1001, 'Super Admin', 'superadmin@example.com', 'SUPER_ADMIN', 'ALL_ACCESS', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO admin_user (user_id, user_name, email, admin_type, privilege_value, status, created_at, updated_at)
VALUES (1002, 'Admin User', 'admin@example.com', 'ADMIN', 'WRITE_ACCESS', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO admin_user (user_id, user_name, email, admin_type, privilege_value, status, created_at, updated_at)
VALUES (1003, 'Viewer User', 'viewer@example.com', 'VIEWER', 'READ_ONLY', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 1. Generate 10,000 Admin Users first (IDs 20001 to 30000) to satisfy the subset requirement
INSERT INTO admin_user (user_id, user_name, email, admin_type, privilege_value, status, created_at, updated_at)
WITH RECURSIVE admin_gen(x) AS (
    SELECT 1
    UNION ALL
    SELECT x + 1 FROM admin_gen WHERE x < 10000
)
SELECT
    20000 + x,
    'Generated Admin ' || x,
    'gen_admin' || x || '@example.com',
    'VIEWER',
    'READ_ONLY',
    'ACTIVE',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
FROM admin_gen;

-- 2. Generate 10,000 Users in 'usr' table (IDs 20001 to 30000) matching the Admin Users
-- Updated to include password and salt
-- Password: "password123", Salt: "salt123"
-- Hash: "5b722b307fce6c944905d132691d5e4a2214b7fe92b738920eb3fce3a90420a19256c32e96c85724a896651f9421735d" (Example hash)
INSERT INTO usr (user_id, company_id, employee_number, global_name, local_name, nickname, email_address, single_id, country_name, primary_phone_number, business_phone_number, voip_phone_number, department_name, department_code, password, salt, created_unix_time, updated_unix_time)
WITH RECURSIVE usr_gen(x) AS (
    SELECT 1
    UNION ALL
    SELECT x + 1 FROM usr_gen WHERE x < 10000
)
SELECT
    20000 + x,
    'COMP' || x,
    'EMP' || x,
    'Global User ' || x,
    'Local User ' || x,
    'Nick' || x,
    'user' || x || '@test.com',
    'SID' || x,
    'USA',
    '1234567890',
    '0987654321',
    '1122334455',
    'Engineering',
    'ENG01',
    'Ad95j8WW4+ZPYQHqV8dkwrZB54pD61hEIAUBvhd/jSw=',
    'salt123', -- Dummy Salt
    1706090000,
    1706090000
FROM usr_gen;
-- 3. Existing AppLink Data (100,000 records)
INSERT INTO applink (app_name, app_url, description, status, created_at, updated_at)
WITH RECURSIVE cnt(x) AS (
    SELECT 1
    UNION ALL
    SELECT x + 1 FROM cnt WHERE x < 100000
)
SELECT
    'App ' || x,
    'https://app' || x || '.com',
    'Description for App ' || x,
    'ACTIVE',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
FROM cnt;

-- 4. Theme Data (Manual)
-- Theme Management
INSERT INTO theme_management (theme_management_id, theme_name, description, is_active, created_at, updated_at)
VALUES (100, 'Dark Theme', 'A dark theme for the admin panel', 'true', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO theme_management (theme_management_id, theme_name, description, is_active, created_at, updated_at)
VALUES (200, 'Light Theme', 'A light theme for the admin panel', 'true', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Theme Version (Linked to Theme Management)
INSERT INTO theme_version (version_id, theme_management_id, version_name, change_log, created_at, updated_at)
VALUES (1, 100, 'v1.0.0', 'Initial release', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO theme_version (version_id, theme_management_id, version_name, change_log, created_at, updated_at)
VALUES (2, 100, 'v1.1.0', 'Bug fixes', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO theme_version (version_id, theme_management_id, version_name, change_log, created_at, updated_at)
VALUES (1, 200, 'v1.0.0', 'Initial release', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Theme File Metadata (Linked to Theme Version)
INSERT INTO theme_file_metadata (theme_file_meta_id, theme_version_id, theme_management_id, file_name, file_path, file_type, file_size, created_at, updated_at)
VALUES (50, 1, 100, 'style.css', '/themes/dark/v1.0.0/style.css', 'CSS', 1024, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO theme_file_metadata (theme_file_meta_id, theme_version_id, theme_management_id, file_name, file_path, file_type, file_size, created_at, updated_at)
VALUES (51, 1, 100, 'index.html', '/themes/dark/v1.0.0/index.html', 'HTML', 2048, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO theme_file_metadata (theme_file_meta_id, theme_version_id, theme_management_id, file_name, file_path, file_type, file_size, created_at, updated_at)
VALUES (60, 2, 100, 'style.css', '/themes/dark/v1.1.0/style.css', 'CSS', 1050, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO theme_file_metadata (theme_file_meta_id, theme_version_id, theme_management_id, file_name, file_path, file_type, file_size, created_at, updated_at)
VALUES (70, 1, 200, 'style.css', '/themes/light/v1.0.0/style.css', 'CSS', 1024, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 5. Bulk Theme Data Generation (1,000 Themes)
-- Generate 1,000 Themes (IDs 1000 to 1999)
INSERT INTO theme_management (theme_management_id, theme_name, description, is_active, created_at, updated_at)
WITH RECURSIVE theme_gen(x) AS (
    SELECT 1000
    UNION ALL
    SELECT x + 1 FROM theme_gen WHERE x < 1999
)
SELECT
    x,
    'Generated Theme ' || x,
    'Description for Generated Theme ' || x,
    'true',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
FROM theme_gen;

-- Generate Versions for those 1,000 Themes (2 versions per theme: v1 and v2)
INSERT INTO theme_version (version_id, theme_management_id, version_name, change_log, created_at, updated_at)
WITH RECURSIVE theme_gen(x) AS (
    SELECT 1000
    UNION ALL
    SELECT x + 1 FROM theme_gen WHERE x < 1999
)
SELECT 1, x, 'v1.0.0', 'Initial Release', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP FROM theme_gen
UNION ALL
SELECT 2, x, 'v2.0.0', 'Major Update', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP FROM theme_gen;

-- Generate File Metadata (2 files per version per theme)
-- File 1: style.css for v1
INSERT INTO theme_file_metadata (theme_file_meta_id, theme_version_id, theme_management_id, file_name, file_path, file_type, file_size, created_at, updated_at)
WITH RECURSIVE theme_gen(x) AS (
    SELECT 1000
    UNION ALL
    SELECT x + 1 FROM theme_gen WHERE x < 1999
)
SELECT (x * 10) + 1, 1, x, 'style.css', '/themes/' || x || '/v1/style.css', 'CSS', 1024, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP FROM theme_gen;

-- File 2: index.html for v1
INSERT INTO theme_file_metadata (theme_file_meta_id, theme_version_id, theme_management_id, file_name, file_path, file_type, file_size, created_at, updated_at)
WITH RECURSIVE theme_gen(x) AS (
    SELECT 1000
    UNION ALL
    SELECT x + 1 FROM theme_gen WHERE x < 1999
)
SELECT (x * 10) + 2, 1, x, 'index.html', '/themes/' || x || '/v1/index.html', 'HTML', 2048, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP FROM theme_gen;
