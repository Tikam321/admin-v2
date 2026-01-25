package com.tikam.simple_admin_v2.dto.admin;

import lombok.Data;

@Data
public class SearchRequest {
    private int page = 0;
    private int size = 10;
    // Add other search fields as needed
}
