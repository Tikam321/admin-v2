package com.tikam.simple_admin_v2.config.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;
@Slf4j
public class RoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
        if (isReadOnly) {
            log.info(">>> Routing to SLAVE data source"); // <-- ADD THIS
            return DataSourceType.SLAVE;
        } else {
            log.info(">>> Routing to MASTER data source"); // <-- ADD THIS
            return DataSourceType.MASTER;
        }
    }
}
