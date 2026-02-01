package com.tikam.simple_admin_v2.controller;

import com.tikam.simple_admin_v2.document.PolicyHistory;
import com.tikam.simple_admin_v2.dto.APIResponse;
import com.tikam.simple_admin_v2.dto.PolicyHistoryRequest;
import com.tikam.simple_admin_v2.service.PolicyHistoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/policy-history")
@RequiredArgsConstructor
@Validated
public class PolicyHistoryController {

    private final PolicyHistoryService policyHistoryService;

    @PostMapping
    public ResponseEntity<APIResponse<Void>> addPolicyHistory(@Valid @RequestBody PolicyHistoryRequest request) {
        policyHistoryService.savePolicyHistory(
                request.getUsername(),
                request.getTargetCategory(),
                request.getTargetName(),
                request.getPolicyName(),
                request.getPrevValue(),
                request.getNextValue(),
                request.getAction()
        );
        return new ResponseEntity<>(APIResponse.created(null), HttpStatus.CREATED);
    }

    @GetMapping
    public Page<PolicyHistory> searchPolicyHistory(
            @RequestParam(required = false)  LocalDate startDate,
            @RequestParam(required = false)  LocalDate endDate,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        // Default to last 30 days if null
//        if (startDate == null) startDate = LocalDate.now().minusDays(30);
//        if (endDate == null) endDate = LocalDate.now();
        System.out.println("the controller is running");

        return policyHistoryService.search(startDate, endDate, keyword, page, size);
//        System.out.println("the controller is not running");
    }

}
