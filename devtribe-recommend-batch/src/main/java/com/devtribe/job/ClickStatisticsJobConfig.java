package com.devtribe.job;

import com.devtribe.domain.post.entity.PostClickLogDocument;
import com.devtribe.step.ClickLogWriter;
import com.devtribe.step.ElasticsearchSearchAfterItemReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
public class ClickStatisticsJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final ElasticsearchOperations elasticsearchOperations;
    private final ClickLogWriter clickLogWriter;

    public ClickStatisticsJobConfig(
        JobRepository jobRepository,
        PlatformTransactionManager transactionManager,
        ElasticsearchOperations elasticsearchOperations,
        ClickLogWriter clickLogWriter
    ) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.elasticsearchOperations = elasticsearchOperations;
        this.clickLogWriter = clickLogWriter;
    }

    @Bean
    public Job clickStatisticsJob() {
        return new JobBuilder("clickStatisticsJob", jobRepository)
            .start(aggregateByCareerInterestAndLevel())
            .build();
    }

    @Bean
    public Step aggregateByCareerInterestAndLevel() {
        return new StepBuilder("aggregateByCareerInterestAndLevel", jobRepository)
            .<PostClickLogDocument, PostClickLogDocument>chunk(500, transactionManager)
            .reader(postClickReader())
            .writer(clickLogWriter)
            .build();
    }

    @Bean
    public ElasticsearchSearchAfterItemReader<PostClickLogDocument> postClickReader() {
        return new ElasticsearchSearchAfterItemReader<>(
            elasticsearchOperations,
            "post-click-logs",
            10,
            PostClickLogDocument.class,
            "timestamp"
        );
    }
}
