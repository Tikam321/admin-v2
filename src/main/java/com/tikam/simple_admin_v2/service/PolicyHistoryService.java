package com.tikam.simple_admin_v2.service;

import com.tikam.simple_admin_v2.document.PolicyHistory;
import com.tikam.simple_admin_v2.repository.PolicyHistoryRepository;
import com.tikam.simple_admin_v2.repository.query.PolicyHistorySearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class PolicyHistoryService {

    private final PolicyHistoryRepository policyHistoryRepository;
    private final PolicyHistorySearchRepository policyHistorySearchRepository;

    public void savePolicyHistory(String username,
                                  PolicyHistory.TargetCategory targetCategory,
                                  String targetName,
                                  String policyName,
                                  String prevValue,
                                  String nextValue,
                                  PolicyHistory.Action action) {

        PolicyHistory history = PolicyHistory.builder()
                .username(username)
                .targetCategory(targetCategory)
                .targetName(targetName)
                .policyName(policyName)
                .prevValue(prevValue)
                .nextValue(nextValue)
                .modificationDate(new Date()) // Changed to java.util.Date
                .action(action)
                .build();

        try {
            policyHistoryRepository.save(history);
            log.info("Successfully saved policy history for user: {}", username);
        } catch (Exception e) {
            log.error("Failed to save policy history for user: {}. Error: {}", username, e.getMessage());
        }
    }

    public Page<PolicyHistory> search(LocalDate startDate, LocalDate endDate, String keyword, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return policyHistorySearchRepository.searchHistory(startDate, endDate, keyword, pageable);
    }
}
