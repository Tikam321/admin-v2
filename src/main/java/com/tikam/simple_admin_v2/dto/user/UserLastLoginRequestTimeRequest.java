package com.tikam.simple_admin_v2.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tikam.simple_admin_v2.enums.Language;
import com.tikam.simple_admin_v2.enums.SearchType.SearchType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Singular;

import static com.tikam.simple_admin_v2.enums.Language.EN;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class UserLastLoginRequestTimeRequest {
    @NotNull
    private String companyCode;
    private String suborgCode;
    @JsonProperty(defaultValue = "EN")
    private Language language;
    @JsonProperty(defaultValue = "DEVICE")
    private SearchType searchType;
    @NotNull
    @Size(max = 100,min = 10)
    private String reason;



}
