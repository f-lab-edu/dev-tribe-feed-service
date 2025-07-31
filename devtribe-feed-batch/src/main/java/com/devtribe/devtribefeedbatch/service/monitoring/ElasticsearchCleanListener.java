package com.devtribe.devtribefeedbatch.service.monitoring;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ElasticsearchCleanListener implements JobExecutionListener {

    private final ElasticsearchClient elasticsearchClient;

    public ElasticsearchCleanListener(ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (isJobFinished(jobExecution)) {
            try {
                elasticsearchClient._transport().close();
                log.info("Elasticsearch cleaned.");
            } catch (IOException e) {
                log.error("Error closing elasticsearch client", e);
                throw new RuntimeException(e);
            }
        }
    }

    private boolean isJobFinished(JobExecution jobExecution) {
        return jobExecution.getStatus() == BatchStatus.COMPLETED
            || jobExecution.getStatus() == BatchStatus.FAILED
            || jobExecution.getStatus() == BatchStatus.STOPPED;
    }
}
