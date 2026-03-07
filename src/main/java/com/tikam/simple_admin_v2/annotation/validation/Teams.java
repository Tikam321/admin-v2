package com.tikam.simple_admin_v2.annotation.validation;

import org.springframework.context.annotation.Profile;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Profile({"kt-dev", "kt-prod"})
@Retention(RetentionPolicy.RUNTIME)
public @interface Teams {

}
