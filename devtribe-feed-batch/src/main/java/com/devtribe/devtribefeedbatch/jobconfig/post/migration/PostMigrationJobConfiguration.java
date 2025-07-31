package com.devtribe.devtribefeedbatch.jobconfig.post.migration;

import com.devtribe.devtribefeedbatch.service.monitoring.BatchJobExecutionListener;
import com.devtribe.devtribefeedbatch.service.monitoring.ElasticsearchCleanListener;
import com.devtribe.domain.post.entity.Post;
import com.devtribe.domain.post.entity.PostDocument;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class PostMigrationJobConfiguration {

    private static final String JOB_NAME = "postMigrationJob";
    private static final int CHUNK_SIZE = 1000;
    private static final int PAGE_SIZE = 1000;

    private final ElasticsearchCleanListener elasticsearchCleanListener;
    private final BatchJobExecutionListener batchJobExecutionListener;

    public PostMigrationJobConfiguration(
        ElasticsearchCleanListener elasticsearchCleanListener,
        BatchJobExecutionListener batchJobExecutionListener) {
        this.elasticsearchCleanListener = elasticsearchCleanListener;
        this.batchJobExecutionListener = batchJobExecutionListener;
    }

    /// 초기화 잡
    @Bean
    public Job postMigrationJob(
        JobRepository jobRepository,
        Step postMigrationStep
    ) {
        return new JobBuilder(JOB_NAME, jobRepository)
            .listener(batchJobExecutionListener)
            .listener(elasticsearchCleanListener)
            .start(postMigrationStep)
            .build();
    }

    @Bean
    @JobScope
    public Step postMigrationStep(
        JobRepository jobRepository,
        PlatformTransactionManager transactionManager,
        JpaPagingItemReader<Post> itemReader,
        ItemProcessor<Post, PostDocument> itemProcessor,
        ItemWriter<PostDocument> itemWriter
    ) {
        return new StepBuilder("migrationStep", jobRepository)
            .<Post, PostDocument>chunk(CHUNK_SIZE, transactionManager)
            .reader(itemReader)
            .processor(itemProcessor)
            .writer(itemWriter)
            .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<Post> postPagingItemReader(
        EntityManagerFactory entityManagerFactory
    ) {
        return new JpaPagingItemReaderBuilder<Post>()
            .entityManagerFactory(entityManagerFactory)
            .name("postPagingItemReader")
            .queryString("select p from Post p")
            .pageSize(PAGE_SIZE)
            .build();
    }

    @Bean
    public ItemProcessor<Post, PostDocument> postDocumentItemProcessor() {
        return PostDocument::of;
    }

    @Bean
    @StepScope
    public ItemWriter<PostDocument> postDocumentItemWriter(
        ElasticsearchOperations elasticsearchOperations,
        @Value("#{jobParameters['indexName']}") String indexName
    ) {
        return items -> {
            List<IndexQuery> indexQueries = items.getItems().stream()
                .map(postDocument -> new IndexQueryBuilder()
                    .withId(postDocument.getId().toString())
                    .withObject(postDocument)
                    .withIndex(indexName)
                    .build())
                .toList();

            elasticsearchOperations.bulkIndex(indexQueries, Post.class);
        };
    }

}
