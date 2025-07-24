package com.devtribe.devtribefeedbatch.jobconfig.post.migration

import com.devtribe.devtribefeedbatch.DevtribeFeedBatchApplication
import com.devtribe.devtribefeedbatch.ElasticsearchContainerConfig
import com.devtribe.devtribefeedbatch.service.post.PostBatchService
import com.devtribe.domain.post.entity.Post
import com.devtribe.domain.post.entity.PostDocument
import org.springframework.batch.core.*
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.batch.test.context.SpringBatchTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.data.elasticsearch.client.elc.NativeQuery
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import spock.lang.Specification

@Import(ElasticsearchContainerConfig.class)
@SpringBatchTest
@SpringJUnitConfig(classes = [DevtribeFeedBatchApplication.class])
@TestPropertySource(properties = ["spring.batch.job.name=postMigrationJob"])
class PostMigrationJobConfigurationTest extends Specification {

    private String indexName = "post-index"

    @Autowired
    JobLauncherTestUtils jobLauncherTestUtils

    @Autowired
    ElasticsearchOperations elasticsearchOperations

    @Autowired
    PostBatchService postBatchService

    @Autowired
    Job postMigrationJob

    void "게시글 엘라스틱서치 마이그레이션 테스트"() {
        given:
        postBatchService.saveAll(List.of(
                new Post("title", "content", 1L, "thumbnail"),
                new Post("title1", "content", 1L, "thumbnail"),
                new Post("title2", "content", 1L, "thumbnail"),
        ))
        def jobParameters = getJobParameters()
        jobLauncherTestUtils.setJob(postMigrationJob)

        when:
        def jobExecution = jobLauncherTestUtils.launchJob(jobParameters)

        then:
        jobExecution.getExitStatus().getExitCode() == ExitStatus.COMPLETED.getExitCode()
        countAllPostDocuments() == 3L
    }

    private JobParameters getJobParameters() {
        new JobParametersBuilder()
                .addJobParameter("indexName", new JobParameter<>(indexName, String.class, false))
                .toJobParameters()
    }

    private long countAllPostDocuments() {
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q.matchAll(m -> m))
                .build();

        elasticsearchOperations.indexOps(IndexCoordinates.of(indexName)).refresh(); /// 명시적 refresh
        return elasticsearchOperations.count(query, PostDocument.class);
    }

}
