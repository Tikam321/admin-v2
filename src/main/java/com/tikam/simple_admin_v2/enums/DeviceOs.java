package com.tikam.simple_admin_v2.enums;

import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Getter
public enum DeviceOs {
    ANDROID(0,"android"),
    WINDOW(1,"window"),
    UNKNOWN(2,"unknown"),
    MACOS(3,"macos");
    private final int code;
    private final String value;
    DeviceOs(int code, String value) {
        this.code = code;
        this.value = value;
    }
    private static final Map<Integer, DeviceOs> deviceTypeCodeMap =
            Collections.unmodifiableMap(Stream.of(values()).collect(Collectors.toMap(
                    DeviceOs::getCode,
                    Function.identity()
            )));

    private static final  Map<String, DeviceOs> deviceTypeValueMap =
            Collections.unmodifiableMap(Stream.of(values()).collect(Collectors.toMap(
                    DeviceOs::getValue,
                    Function.identity()
            )));
    public static DeviceOs from(int code) {
        return deviceTypeCodeMap.getOrDefault(code, UNKNOWN);

    }
    public static DeviceOs from(String value) {
        return deviceTypeValueMap.getOrDefault(value, UNKNOWN);
    }
    public static boolean isValid(String value) {
        return deviceTypeValueMap.containsKey(value);
    }
}
