-- Dummy Data for Policy-Related Tables

-- 1. Populate 'plc_rl' (PolicyRule) with 10 sample rules
-- Corrected column names for BaseDateTimeEntity fields: created_at, updated_at
INSERT INTO plc_rl (
    plc_rl_id, rl_txt, en_rl_txt, rl_typ_val, deliv_clnt_yn,
    deliv_tnt_admin_yn, plc_grp_id, data_type, plc_unit, pmt_ctrl_val,
    org_plc_rl_yn, secu_grd_plc_rl_yn, usr_plc_rl_yn, plc_align_no,
    created_at, updated_at
) VALUES
(1, 'Allow USB Storage', 'Allow USB Storage', 'DEVICE_CONTROL', true, 0, 1, 'BOOLEAN', null, null, 1, 0, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Block Camera', 'Block Camera', 'DEVICE_CONTROL', true, 0, 1, 'BOOLEAN', null, null, 1, 0, 1, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Require Strong Password', 'Require Strong Password', 'SECURITY', true, 0, 2, 'REGEX', null, null, 1, 0, 0, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'Set Screen Timeout', 'Set Screen Timeout', 'SECURITY', true, 0, 2, 'INTEGER', 'seconds', '300', 1, 0, 0, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 'Enable File Encryption', 'Enable File Encryption', 'ENCRYPTION', true, 0, 3, 'BOOLEAN', null, null, 1, 0, 0, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 'Block App Store', 'Block App Store', 'APPLICATION', true, 0, 4, 'BOOLEAN', null, null, 1, 0, 0, 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 'Allow Specific Wi-Fi', 'Allow Specific Wi-Fi', 'NETWORK', true, 0, 5, 'STRING_LIST', null, null, 1, 0, 0, 7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 'Disable Bluetooth', 'Disable Bluetooth', 'DEVICE_CONTROL', true, 0, 1, 'BOOLEAN', null, null, 1, 0, 1, 8, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 'Force VPN Connection', 'Force VPN Connection', 'NETWORK', true, 0, 5, 'BOOLEAN', null, null, 1, 0, 0, 9, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 'Minimum OS Version', 'Minimum OS Version', 'SECURITY', true, 0, 2, 'STRING', null, '12.0', 1, 0, 0, 10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 2. Populate 'usr_plc_rl' (UserPolicyRule) - Link users to specific rules
INSERT INTO usr_plc_rl (usr_id, plc_rl_id, ctrl_val, created_at, updated_at)
VALUES
(20001, 1, 'true', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(20002, 1, 'true', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 3. Populate 'org_plc_rl' (OrgPolicyRule) - Link organizations to specific rules
INSERT INTO org_plc_rl (comp_cd, suborg_cd, plc_rl_id, ctrl_val, created_at, updated_at)
VALUES
('C60', 'TELE', 3, '^(?=.*[A-Z])(?=.*[0-9]).{8,}$', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('C60', 'TELE', 4, '300', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 4. Populate 'cmpny_lcs_plc' (CompanyLicensePolicy) - Create main policy groups
INSERT INTO cmpny_lcs_plc (cmpny_lcs_plc_id, lcs_id, cmpny_id, plc_typ_cd, plc_nm, plc_desc, use_yn, created_at, updated_at)
VALUES
(101, 1, 1, 1, 'global feature policy', 'Standard security settings for all employees.', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(102, 1, 1, 2, 'company policy'," for company feature policy" 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 5. Populate 'cmpny_lcs_plc_rl' (CompanyLicensePolicyRule) - Link policy groups to specific rules
INSERT INTO cmpny_lcs_plc_rl (cmpny_lcs_plc_id, plc_rl_id, ctrl_val, created_at, updated_at)
VALUES
(101, 8, 'true', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(101, 10, '12.0', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(102, 2, 'true', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(102, 5, 'true', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(102, 9, 'true', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
