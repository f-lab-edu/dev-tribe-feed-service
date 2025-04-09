package com.devtribe.devtribe_feed_service.integration;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DatabaseCleaner implements InitializingBean {

    private static final String FOREIGN_KEY_CHECK_FALSE = "SET FOREIGN_KEY_CHECKS = 0";
    private static final String FOREIGN_KEY_CHECK_TRUE = "SET FOREIGN_KEY_CHECKS = 1";
    private static final Logger log = LogManager.getLogger(DatabaseCleaner.class);

    private final List<String> tableNames = new ArrayList<>();

    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    private void findDatabaseTableNames() {
        try {
            List<String> tableNameList = em.createNativeQuery("SHOW TABLES").getResultList();
            tableNameList.stream()
                .filter(tableName -> !tableName.equals("flyway_schema_history"))
                .forEach(this.tableNames::add);
        } catch (Exception e) {
            log.info("Failed to find database table names", e);
            throw new IllegalStateException(e);
        }
    }

    private void updateForeignKeyChecks(String foreignKeyCheckFalse) {
        em.createNativeQuery(foreignKeyCheckFalse).executeUpdate();
    }

    private void truncateTables() {
        tableNames.forEach(tableName ->
            updateForeignKeyChecks(String.format("TRUNCATE TABLE %s", tableName))
        );
    }

    private void resetSequences() {
        tableNames.forEach(tableName ->
            updateForeignKeyChecks(String.format("ALTER TABLE %s AUTO_INCREMENT = 1", tableName))
        );
    }

    @Transactional
    public void clear() {
        updateForeignKeyChecks(FOREIGN_KEY_CHECK_FALSE);
        truncateTables();
        resetSequences();
        updateForeignKeyChecks(FOREIGN_KEY_CHECK_TRUE);
        em.clear();
    }

    @Override
    public void afterPropertiesSet() {
        findDatabaseTableNames();
    }
}
