# OAuth2 and OpenID Connect (OIDC) -- Clear Explanation with Examples

------------------------------------------------------------------------

## 1. What is OAuth2?

OAuth 2.0 is an **authorization framework** that allows a third-party
application to access a user's protected resources **without sharing the
user's password**.

### Core Idea:

OAuth2 answers the question:

> "What is this application allowed to access?"

------------------------------------------------------------------------

## OAuth2 Roles

1.  **Resource Owner** -- The user
2.  **Client** -- The application requesting access
3.  **Authorization Server** -- Issues access tokens
4.  **Resource Server** -- Hosts protected resources (APIs)

------------------------------------------------------------------------

## OAuth2 Example -- Slack Accessing Google Drive

### Scenario:

Slack wants permission to access a user's Google Drive files.

### Flow:

1.  Slack redirects user to Google.
2.  User logs in and grants permission.
3.  Google issues an **Access Token**.
4.  Slack uses the access token to call Google Drive API.

### Important:

-   Slack never sees the user's password.
-   Slack only gets permission for allowed scopes (e.g., read files).

------------------------------------------------------------------------

## OAuth2 Grant Types

### 1. Authorization Code Flow (Most Secure)

Used for web and mobile apps.

### 2. Client Credentials Flow

Used for service-to-service communication.

Example: Service A gets token from Auth Server → calls Service B.

### 3. Resource Owner Password Flow (Deprecated)

Client directly collects username/password.

------------------------------------------------------------------------

## Access Token

Access token is used to access protected APIs.

Example:

Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

------------------------------------------------------------------------

# 2. What is OpenID Connect (OIDC)?

OpenID Connect (OIDC) is an **authentication layer built on top of
OAuth2**.

### Core Idea:

OIDC answers the question:

> "Who is the user?"

------------------------------------------------------------------------

## What OIDC Adds on Top of OAuth2

1.  **ID Token (JWT)**
2.  Standard identity claims
3.  UserInfo endpoint
4.  Standard scopes like:
    -   openid
    -   profile
    -   email

------------------------------------------------------------------------

## ID Token Example (JWT)

``` json
{
  "sub": "1234567890",
  "email": "user@gmail.com",
  "name": "John Doe",
  "exp": 1712345678
}
```

ID Token is used for authentication.

------------------------------------------------------------------------

## OIDC Example -- Login with Google

### Scenario:

User clicks "Login with Google".

### Flow:

1.  App redirects user to Google.
2.  User logs in.
3.  Google returns an **ID Token**.
4.  App verifies ID token signature.
5.  App creates its own session.

Important: - The app does NOT get the user password. - It only gets
identity claims.

------------------------------------------------------------------------

# OAuth2 vs OIDC Comparison

Feature              OAuth2          OIDC
  -------------------- --------------- ----------------
Purpose              Authorization   Authentication
Main Token           Access Token    ID Token
Identity Guarantee   No              Yes
Built on OAuth2      No              Yes

------------------------------------------------------------------------

# Real-World Use Cases

### OAuth2

-   Slack accessing Google Drive
-   CI/CD tools accessing GitHub
-   Microservice-to-microservice authentication

### OIDC

-   Login with Google
-   Login with Facebook
-   Enterprise SSO (Okta, Azure AD)

------------------------------------------------------------------------

# Summary

-   OAuth2 = Permission framework (Authorization)
-   OIDC = Identity layer on top of OAuth2 (Authentication)
-   OAuth2 can exist without OIDC.
-   OIDC always uses OAuth2 underneath.

------------------------------------------------------------------------

End of Document
