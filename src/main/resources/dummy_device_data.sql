-- Dummy Data for CSQ_TBT, Device, UserDevice, and MsgPrxConn Tables

-- 1. Populate CSQ_TBT (Employee Info)
INSERT INTO csq_tbt (ep_id, company_code, sub_org_code, department_code, company_name)
WITH RECURSIVE gen_limit(x) AS (
    SELECT 1
    UNION ALL
    SELECT x + 1 FROM gen_limit WHERE x <= 10000
)
SELECT
    'EMP' || (100 + x),
    'C60',
    'TELE',
    'DEPT123',
    'Company ' || (100 + x)
FROM gen_limit;

-- 2. Populate device_tbt (Device Info)
INSERT INTO device_tbt (dvc_id, imei, modl_nm, os_type_code, os_version, app_version, device_type, createtime)
WITH RECURSIVE gen_limit(x) AS (
    SELECT 1
    UNION ALL
    SELECT x + 1 FROM gen_limit WHERE x <= 10000
)
SELECT
    'DVC' || (100 + x),
    'IMEI' || (100000 + x),
    'Model ' || (100 + x),
    '0',
    '12.0',
    '1.0.0',
    'PHONE',
    CURRENT_TIMESTAMP
FROM gen_limit;

-- 3. Populate user_device (Link Users to Devices)
INSERT INTO user_device (user_id, dvc_id, is_active, active_status)
WITH RECURSIVE gen_limit(x) AS (
    SELECT 1
    UNION ALL
    SELECT x + 1 FROM gen_limit WHERE x <= 10000
)
SELECT
    20000 + x, -- Matches user_id range from data.sql
    'DVC' || (100 + x), -- Matches dvc_id range from device_tbt
    true,
    'ACTIVE'
FROM gen_limit;

-- 4. Populate msq_prx_conn (Device Connection Info)
INSERT INTO msq_prx_conn (dvc_id, msg_prx_ip, conn_ums, disconn_time, last_access_utms)
WITH RECURSIVE gen_limit(x) AS (
    SELECT 1
    UNION ALL
    SELECT x + 1 FROM gen_limit WHERE x <= 10000
)
SELECT
    'DVC' || (100 + x), -- Matches dvc_id range from device_tbt
    '192.168.1.1',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
FROM gen_limit;

-- 5. Update the 'usr' table to link user_ep_id to the new csq_tbt ep_id
UPDATE usr
SET user_ep_id = 'EMP' || (100 + (user_id - 20000))
WHERE user_id BETWEEN 20001 AND 30000;
