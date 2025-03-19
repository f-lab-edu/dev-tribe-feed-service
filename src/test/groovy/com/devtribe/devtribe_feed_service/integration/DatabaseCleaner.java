package com.devtribe.devtribe_feed_service.integration;

import jakarta.annotation.PostConstruct;
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
    @PostConstruct
    private void findDatabaseTableNames() {
        try {
            List<String> tableNameList = em.createNativeQuery("SHOW TABLES").getResultList();
            for (String tableName : tableNameList) {
                if (tableName.equals("flyway_schema_history")) continue;
                tableNames.add(tableName);
            }
        } catch (Exception e) {
            log.info("Failed to find database table names", e);
            throw new RuntimeException(e);
        }
    }

    private void truncate() {
        em.createNativeQuery(FOREIGN_KEY_CHECK_FALSE).executeUpdate();
        for (String tableName : tableNames) {
            em.createNativeQuery(String.format("TRUNCATE TABLE %s", tableName)).executeUpdate();
        }
        em.createNativeQuery(FOREIGN_KEY_CHECK_TRUE).executeUpdate();
    }

    @Transactional
    public void clear() {
        truncate();
        em.clear();
    }

    @Override
    public void afterPropertiesSet() {
        findDatabaseTableNames();
    }
}
