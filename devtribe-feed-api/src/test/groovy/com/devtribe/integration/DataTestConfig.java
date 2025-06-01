package com.devtribe.integration;

import com.devtribe.domain.post.dao.FeedRepository;
import com.devtribe.domain.post.dao.FeedRepositoryImpl;
import com.devtribe.domain.post.dao.SortQueryFactory;
import com.devtribe.domain.user.dao.JpaUserRepository;
import com.devtribe.domain.user.dao.UserRepository;
import com.devtribe.domain.user.dao.UserRepositoryImpl;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class DataTestConfig {

    @Bean
    DatabaseCleaner databaseCleaner() {
        return new DatabaseCleaner();
    }

    @Bean
    public UserRepository userRepository(JpaUserRepository jpaUserRepository) {
        return new UserRepositoryImpl(jpaUserRepository);
    }

    @Bean
    public FeedRepository feedRepository(SortQueryFactory sortQueryFactory, JPAQueryFactory jpaQueryFactory) {
        return new FeedRepositoryImpl(sortQueryFactory, jpaQueryFactory);
    }

    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public SortQueryFactory sortQueryFactory() {
        return new SortQueryFactory();
    }

}
