package com.tikam.simple_admin_v2.dto.applink;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AppLinkResponseList {
    private List<AppLinkResponse> list;
}
