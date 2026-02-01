package com.tikam.simple_admin_v2.repository.query;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.json.JsonData;
import com.tikam.simple_admin_v2.document.PolicyHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PolicyHistorySearchRepository {
    private final ElasticsearchOperations esOperations;

    public Page<PolicyHistory> searchHistory(LocalDate startDate, LocalDate endDate,
                                             String keyword, PageRequest pageable) {
        BoolQuery.Builder boolQuery = new BoolQuery.Builder();

        // 1. Date Range Filter
        if (startDate != null && endDate != null) {
            // Convert LocalDate to java.util.Date for the query
            Date start = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date end = Date.from(endDate.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant());

            boolQuery.filter(f -> f.range(r -> r
                    .field("modificationDate")
                    .gte(JsonData.of(start))
                    .lte(JsonData.of(end))
            ));
        }

        // 2. Keyword Search
        searchByTargetName(boolQuery, keyword);
        if (keyword != null && !keyword.isEmpty()) {
            boolQuery.must(m -> m.queryString(qs -> qs
                    .fields("username", "targetName", "policyName")
                    .query("*" + keyword + "*")
                    .analyzeWildcard(true)
            ));
        }

        NativeQuery searchQuery = new NativeQueryBuilder()
                .withQuery(boolQuery.build()._toQuery())
                .withPageable(pageable)
                .build();

        SearchHits<PolicyHistory> searchHits = esOperations.search(searchQuery, PolicyHistory.class);

        List<PolicyHistory> results = searchHits.getSearchHits()
                .stream().map(hit -> hit.getContent())
                .toList();

        return new PageImpl<>(results, pageable, searchHits.getTotalHits());
    }

    private void searchByTargetName(BoolQuery.Builder boolQuery, String keyword) {

    }
}
