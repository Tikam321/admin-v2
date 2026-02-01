package com.tikam.simple_admin_v2.dto.applink;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class AppLinkCurserResponse {
    private List<AppLinkResponse> list;
    private Long nextCurser;
    private boolean hasNext;

}
