# Theme Management Database Schema

## 1. Table: `theme_management`
Represents the high-level concept of a "Theme".

| Field Name | Data Type | Constraints | Description |
| :--- | :--- | :--- | :--- |
| `id` | `BIGINT` | `PK`, `AUTO_INCREMENT` | Unique identifier for the theme. |
| `name` | `VARCHAR(100)` | `NOT NULL`, `UNIQUE` | Display name of the theme (e.g., "Dark Mode", "Summer Vibe"). |
| `description` | `VARCHAR(255)` | | Short description of what the theme looks like. |
| `created_at` | `TIMESTAMP` | `DEFAULT CURRENT_TIMESTAMP` | When the theme was first created. |
| `updated_at` | `TIMESTAMP` | `DEFAULT CURRENT_TIMESTAMP` | Last update timestamp. |
| `is_active` | `BOOLEAN` | `DEFAULT TRUE` | Soft delete flag or global enable/disable. |

## 2. Table: `theme_version`
Represents a specific release/version of a theme.
*   **Relationship:** Many-to-One with `theme_management`.

| Field Name | Data Type | Constraints | Description |
| :--- | :--- | :--- | :--- |
| `id` | `BIGINT` | `PK`, `AUTO_INCREMENT` | Unique identifier for this version. |
| `theme_id` | `BIGINT` | `FK` -> `theme_management(id)` | The parent theme this version belongs to. |
| `version` | `VARCHAR(20)` | `NOT NULL` | Version string (e.g., "1.0.0", "1.0.1"). |
| `changelog` | `TEXT` | | Notes on what changed in this version. |
| `status` | `VARCHAR(20)` | `DEFAULT 'DRAFT'` | Enum: `DRAFT`, `PUBLISHED`, `DEPRECATED`. |
| `created_at` | `TIMESTAMP` | `DEFAULT CURRENT_TIMESTAMP` | When this version was uploaded. |
| `published_at` | `TIMESTAMP` | | When this version was made live. |

## 3. Table: `theme_file_metadata`
Stores information about the actual files that make up a theme version.
*   **Relationship:** Many-to-One with `theme_version`.

| Field Name | Data Type | Constraints | Description |
| :--- | :--- | :--- | :--- |
| `id` | `BIGINT` | `PK`, `AUTO_INCREMENT` | Unique identifier for the file metadata. |
| `theme_version_id` | `BIGINT` | `FK` -> `theme_version(id)` | The specific version this file belongs to. |
| `file_name` | `VARCHAR(255)` | `NOT NULL` | Original name of the file (e.g., "styles.css"). |
| `file_path` | `VARCHAR(500)` | `NOT NULL` | Path to where the file is stored (S3 key or local path). |
| `file_type` | `VARCHAR(50)` | `NOT NULL` | MIME type or extension (e.g., "text/css", "image/png"). |
| `file_size` | `BIGINT` | | Size of the file in bytes. |
| `checksum` | `VARCHAR(64)` | | SHA-256 hash for file integrity verification. |
| `created_at` | `TIMESTAMP` | `DEFAULT CURRENT_TIMESTAMP` | When the file was uploaded. |

## Relationships Summary
*   **One** `Theme` has **Many** `ThemeVersions`.
*   **One** `ThemeVersion` has **Many** `ThemeFileMetadata` entries.
