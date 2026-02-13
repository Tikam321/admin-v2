package com.tikam.simple_admin_v2.config;

import com.tikam.simple_admin_v2.dto.auth.UserInfo;
import lombok.experimental.UtilityClass;
import org.springframework.context.annotation.Configuration;
@UtilityClass
public class UserContext {
    private static final ThreadLocal<UserInfo> CONTEXT = new ThreadLocal<>();

    public static void setContext(UserInfo userInfo) {
        CONTEXT.set(userInfo);
    }

    public static UserInfo getContext() {
        return CONTEXT.get();
    }

    public static void clearContext() {
        CONTEXT.remove();
    }

}
