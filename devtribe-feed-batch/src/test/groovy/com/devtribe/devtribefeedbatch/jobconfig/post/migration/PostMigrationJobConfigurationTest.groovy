package com.devtribe.devtribefeedbatch.jobconfig.post.migration


import com.devtribe.devtribefeedbatch.DevtribeFeedBatchApplication
import com.devtribe.devtribefeedbatch.service.post.PostElasticsearchService
import org.springframework.batch.core.*
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.batch.test.context.SpringBatchTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import spock.lang.Specification

@SpringBatchTest
@SpringJUnitConfig(classes = [DevtribeFeedBatchApplication.class])
@TestPropertySource(properties = ["spring.batch.job.name=postMigrationJob"])
class PostMigrationJobConfigurationTest extends Specification {

    private String indexName = "post-v1"

    @Autowired
    JobLauncherTestUtils jobLauncherTestUtils

    @Autowired
    PostElasticsearchService postElasticsearchService

    @Autowired
    Job postMigrationJob

    void "게시글 엘라스틱서치 마이그레이션 테스트"() {
        given:
        /// 게시글 세팅.
        def jobParameters = getJobParameters()
        jobLauncherTestUtils.setJob(postMigrationJob)

        when:
        def jobExecution = jobLauncherTestUtils.launchJob(jobParameters)

        then:
        jobExecution.getExitStatus().getExitCode() == ExitStatus.COMPLETED.getExitCode()
        /// TODO 개수 확인.
        postElasticsearchService.countAllPostDocuments() == 0L
    }

    private JobParameters getJobParameters() {
        new JobParametersBuilder()
                .addJobParameter("indexName", new JobParameter<>(indexName, String.class, false))
                .toJobParameters()
    }

}
