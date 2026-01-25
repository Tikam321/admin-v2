package com.tikam.simple_admin_v2.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CommonPageResponse<T> {
    private List<T> list;
    private long totalCount;
    private int page;
    private int size;
}
