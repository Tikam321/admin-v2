package com.tikam.simple_admin_v2.enums;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum PolicyTreeLevel {
    COMPANY,
    SUBORG;

    private static final Map<String, PolicyTreeLevel> policySearchLevelMap =
            Collections.unmodifiableMap(
                    Stream.of(values()).collect(Collectors.toMap(
                            PolicyTreeLevel::name,
                            Function.identity(),
                            (a,b) -> a,
                            () -> new TreeMap<>(String.CASE_INSENSITIVE_ORDER)
                    ))
            );
    public static PolicyTreeLevel from(String value) {
        return policySearchLevelMap.getOrDefault(value, COMPANY);
    }

}
