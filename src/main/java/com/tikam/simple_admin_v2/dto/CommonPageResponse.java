package com.tikam.simple_admin_v2.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class CommonPageResponse<T> {
     List<T> list;
     long totalCount;
     int page;
     int size;
}
