package com.tikam.simple_admin_v2.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "policy-history")
public class PolicyHistory {

    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String username;

    @Field(type = FieldType.Keyword)
    private TargetCategory targetCategory;

    @Field(type = FieldType.Text)
    private String targetName;

    @Field(type = FieldType.Keyword)
    private String policyName;

    @Field(type = FieldType.Text)
    private String prevValue;

    @Field(type = FieldType.Text)
    private String nextValue;

    // Switched to java.util.Date for maximum compatibility with mixed formats (Z and non-Z)
    @Field(type = FieldType.Date, format = DateFormat.date_time)
    private Date modificationDate;

    @Field(type = FieldType.Keyword)
    private Action action;

    public enum TargetCategory {
        USER, ORGANIZATION
    }

    public enum Action {
        CREATE, UPDATE, DELETE
    }
}
