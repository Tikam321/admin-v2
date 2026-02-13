# Understanding ThreadLocal in Java Web Applications

This document explains the critical role of `ThreadLocal` in the `simple_admin_v2` application, specifically within the `UserContext` class. It details why we use it, how it works, and the severe security risks of not using it.

## 1. The Problem: Handling Concurrent Requests

In a Spring Boot application (running on Tomcat), every incoming HTTP request is handled by a separate **Thread**.

*   **User A** sends a request -> Assigned to **Thread-1**.
*   **User B** sends a request -> Assigned to **Thread-2**.

These threads run in parallel. We need a way to store the **Authenticated User's Identity** (`UserInfo`) so that it is accessible anywhere in the code (Controller, Service, Repository) without passing it as a parameter to every single method.

---

## 2. The Wrong Way: Static Variables (Disaster Scenario)

Imagine if we simply used a static variable to store the user:

```java
// ❌ BAD IMPLEMENTATION
public class UserContext {
    private static UserInfo currentUser; // ONE variable shared by ALL threads

    public static void setUser(UserInfo user) {
        currentUser = user;
    }

    public static UserInfo getUser() {
        return currentUser;
    }
}
```

### The Race Condition (Security Breach)
1.  **Alice** logs in. **Thread-1** calls `setUser(Alice)`. The static variable `currentUser` is now **Alice**.
2.  **Bob** logs in 1 millisecond later. **Thread-2** calls `setUser(Bob)`. The static variable `currentUser` is overwritten to **Bob**.
3.  **Thread-1** (Alice's request) continues to the Controller and calls `getUser()`.
4.  **Result:** It gets **Bob**!
5.  **Consequence:** Alice sees Bob's private data. **Catastrophic Security Failure.**

---

## 3. The Right Way: ThreadLocal (Isolation)

`ThreadLocal` provides **Thread Safety** by giving every thread its own, private copy of the variable.

```java
// ✅ CORRECT IMPLEMENTATION
public class UserContext {
    private static final ThreadLocal<UserInfo> CONTEXT = new ThreadLocal<>();

    public static void setUser(UserInfo user) {
        CONTEXT.set(user); // Stores in Thread-1's private map
    }

    public static UserInfo getUser() {
        return CONTEXT.get(); // Retrieves from Thread-1's private map
    }
    
    public static void clear() {
        CONTEXT.remove(); // Prevents memory leaks
    }
}
```

### How It Works Internally
1.  **Thread-1 (Alice)** calls `set(Alice)`.
    *   Java looks at the current thread (`Thread-1`).
    *   It accesses `Thread-1.threadLocals` (A private Map inside the Thread object).
    *   It stores: `Key: UserContext.CONTEXT` -> `Value: Alice`.

2.  **Thread-2 (Bob)** calls `set(Bob)`.
    *   Java looks at the current thread (`Thread-2`).
    *   It accesses `Thread-2.threadLocals`.
    *   It stores: `Key: UserContext.CONTEXT` -> `Value: Bob`.

3.  **Thread-1** calls `get()`.
    *   It looks in `Thread-1.threadLocals`.
    *   It finds **Alice**. It **cannot** see Bob.

### Visual Analogy: The Hotel Safe
*   **Heap Memory (Static Variable):** A table in the hotel lobby. If Alice puts her wallet there, Bob can take it (or replace it).
*   **ThreadLocal:** A safe in each hotel room.
    *   **Thread-1** is in Room 101.
    *   **Thread-2** is in Room 102.
    *   Alice puts her wallet in Safe 101. Bob puts his wallet in Safe 102.
    *   Even though they are in the same hotel, their valuables are completely isolated.

---

## 4. The Lifecycle in `AuthFilter`

We use a Filter to manage this lifecycle automatically for every request.

```java
public void doFilter(...) {
    try {
        // 1. Authentication
        UserInfo user = authenticate(token);
        
        // 2. Set Context (Store in ThreadLocal)
        UserContext.setUser(user);
        
        // 3. Process Request (Controller -> Service -> Repository)
        chain.doFilter(request, response);
        
    } finally {
        // 4. Cleanup (CRITICAL!)
        UserContext.clear();
    }
}
```

### Why Cleanup is Critical
Tomcat uses a **Thread Pool**. Threads are reused.
1.  **Thread-1** handles Alice's request. `UserContext` has Alice.
2.  Request finishes. If we **don't** clear it...
3.  **Thread-1** is assigned to handle a new request for **Charlie** (who is anonymous).
4.  The code checks `UserContext.getUser()`. It finds **Alice**!
5.  **Result:** Charlie is now logged in as Alice. **Security Breach.**

**Always call `remove()` in a `finally` block.**
