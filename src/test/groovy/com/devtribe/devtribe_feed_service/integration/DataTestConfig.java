package com.devtribe.devtribe_feed_service.integration;

import com.devtribe.devtribe_feed_service.post.repository.FeedRepositoryImpl;
import com.devtribe.devtribe_feed_service.post.repository.PostRepositoryImpl;
import com.devtribe.devtribe_feed_service.post.repository.jpa.JpaPostRepository;
import com.devtribe.devtribe_feed_service.post.repository.query.QueryFeedBySortOption;
import com.devtribe.devtribe_feed_service.post.repository.query.SortQueryFactory;
import com.devtribe.devtribe_feed_service.user.repository.UserRepositoryImpl;
import com.devtribe.devtribe_feed_service.user.repository.jpa.JpaUserRepository;
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
    public UserRepositoryImpl userRepository(JpaUserRepository jpaUserRepository) {
        return new UserRepositoryImpl(jpaUserRepository);
    }

    @Bean
    public PostRepositoryImpl postRepository(JpaPostRepository jpaPostRepository) {
        return new PostRepositoryImpl(jpaPostRepository);
    }

    @Bean
    public FeedRepositoryImpl feedRepository(QueryFeedBySortOption queryFeedBySortOption) {
        return new FeedRepositoryImpl(queryFeedBySortOption);
    }

    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public QueryFeedBySortOption queryFeedBySortOption(SortQueryFactory sortQueryFactory,
        JPAQueryFactory jpaQueryFactory) {
        return new QueryFeedBySortOption(sortQueryFactory, jpaQueryFactory);
    }

    @Bean
    public SortQueryFactory sortQueryFactory() {
        return new SortQueryFactory();
    }

}
