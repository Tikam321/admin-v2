# Request Lifecycle: Filters vs. Interceptors

This document explains the flow of an HTTP request through the `simple_admin_v2` application, specifically focusing on how Authentication (Filters) and Authorization (Interceptors) are handled.

## The Journey of a Request

When a client sends a request (e.g., `GET /admin/api/themes`), it passes through several layers before reaching your business logic.

```mermaid
graph TD
    Client[Client Request] --> Filter[1. AuthFilter (Servlet Filter)]
    Filter --> Dispatcher[DispatcherServlet (Spring MVC)]
    Dispatcher --> Interceptor[2. AuthInterceptor (HandlerInterceptor)]
    Interceptor --> Controller[3. ThemeController]
    
    subgraph "Authentication Layer"
    Filter
    end
    
    subgraph "Authorization Layer"
    Interceptor
    end
    
    subgraph "Business Logic"
    Controller
    end
```

---

## 1. The Outer Gate: Filters (Servlet Container)

**Component:** `AuthFilter.java`
**Scope:** Runs for *every* request matching the URL pattern (e.g., `/*`).
**Responsibility:** **Authentication** (Who are you?)

### What happens here?
1.  **Intercept:** The filter catches the incoming HTTP request.
2.  **Validate:** It checks the `Authorization` header for a valid JWT.
    *   Calls `JwtUtils.validateToken()`.
3.  **Identify:** If valid, it extracts the `userId` and `role`.
4.  **Context:** It populates the `UserContext` (ThreadLocal) with this identity.
    *   *Analogy:* The security guard checks your ID card and gives you a visitor badge.
5.  **Proceed:** It passes the request down the chain.

**Key Code:**
```java
UserContext.setUser(userInfo);
filterChain.doFilter(request, response);
UserContext.clear(); // Cleanup on return
```

---

## 2. The Dispatcher: Spring MVC

**Component:** `DispatcherServlet`
**Responsibility:** Routing

The request leaves the Servlet world and enters the Spring MVC world. The Dispatcher looks at the URL and decides which Controller method should handle it.

---

## 3. The Inner Checkpoint: Interceptors (Spring MVC)

**Component:** `AuthorizationInterceptor` (To be implemented)
**Scope:** Runs *after* routing but *before* the Controller method.
**Responsibility:** **Authorization** (Are you allowed to be here?)

### What happens here?
1.  **Inspect:** The interceptor looks at the specific Controller method being called.
    *   It can check for annotations like `@RequiresRole("ADMIN")`.
2.  **Check Context:** It retrieves the `UserInfo` from `UserContext` (set by the Filter).
3.  **Decide:**
    *   **Allow:** If the user has the required role, return `true`.
    *   **Block:** If the user lacks permission, return `false` (send 403 Forbidden).

**Key Code:**
```java
UserInfo user = UserContext.getUser();
if (user == null || !user.getRole().equals("ADMIN")) {
    response.sendError(403);
    return false;
}
return true;
```

---

## 4. The Destination: Controller

**Component:** `ThemeController.java`
**Responsibility:** Business Logic

By the time the request reaches here:
1.  We know **who** the user is (thanks to the Filter).
2.  We know they are **allowed** to be here (thanks to the Interceptor).

The Controller can simply focus on fetching data, knowing the security checks are already done.

---

## Summary Table

| Feature | Filter (`AuthFilter`) | Interceptor (`AuthInterceptor`) |
| :--- | :--- | :--- |
| **Layer** | Servlet (Low Level) | Spring MVC (High Level) |
| **Timing** | Before DispatcherServlet | After DispatcherServlet |
| **Primary Job** | **Authentication** (JWT Validation) | **Authorization** (Role Checks) |
| **Context Access** | Limited (Request/Response) | Full (Controller Method, Annotations) |
| **Analogy** | Building Security Guard | Executive Floor Receptionist |
