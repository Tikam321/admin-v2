package com.tikam.simple_admin_v2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class APIResponse<T> {
    private boolean success;
    private String message;
    private T data;

    public static <T> APIResponse<T> ok(T data) {
        return APIResponse.<T>builder()
                .success(true)
                .message("Success")
                .data(data)
                .build();
    }

    public static <T> APIResponse<T> created(T data) {
        return APIResponse.<T>builder()
                .success(true)
                .message("Created")
                .data(data)
                .build();
    }

    public static <T> APIResponse<T> updated(T data) {
        return APIResponse.<T>builder()
                .success(true)
                .message("Updated")
                .data(data)
                .build();
    }

    public static <T> APIResponse<T> deleted() {
        return APIResponse.<T>builder()
                .success(true)
                .message("Deleted")
                .data(null)
                .build();
    }
}
