package com.tikam.simple_admin_v2.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum DeviceType {
    MOBILE(0,"mobile"),
    PUBLIC(1,"public pc"),
    PC(2,"pc"),
    UNKNOWN(3,"unknown"),
    RELATION(4,"relation"),
    WEB(5,"web"),
    PA(6,"PA"),
    DUMMY(7,"dummy");
    private final int code;
    private final String value;
    DeviceType(int code, String value) {
        this.code = code;
        this.value = value;
    }




    private static final Map<Integer, DeviceType> deviceTypeCodeMap =
            Collections.unmodifiableMap(Stream.of(values()).collect(Collectors.toMap(
                            DeviceType::getCode,
                            Function.identity()
                    )));

    private static final  Map<String, DeviceType> deviceTypeValueMap =
            Collections.unmodifiableMap(Stream.of(values()).collect(Collectors.toMap(
                    DeviceType::getValue,
                    Function.identity()
            )));
    public static DeviceType from(int code) {
        return deviceTypeCodeMap.getOrDefault(code, UNKNOWN);

    }
    public static DeviceType from(String value) {
        return deviceTypeValueMap.getOrDefault(value, UNKNOWN);
    }
    public static boolean isValid(String value) {
        return deviceTypeValueMap.containsKey(value);
    }
}
