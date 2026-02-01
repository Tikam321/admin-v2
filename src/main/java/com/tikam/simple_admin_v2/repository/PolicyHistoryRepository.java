package com.tikam.simple_admin_v2.repository;

import com.tikam.simple_admin_v2.document.PolicyHistory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicyHistoryRepository extends ElasticsearchRepository<PolicyHistory, String> {
}
