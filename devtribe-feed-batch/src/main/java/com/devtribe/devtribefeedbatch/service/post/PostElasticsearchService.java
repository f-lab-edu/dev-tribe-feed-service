package com.devtribe.devtribefeedbatch.service.post;

import com.devtribe.domain.post.entity.Post;
import com.devtribe.domain.post.entity.PostDocument;
import java.time.Duration;
import java.util.List;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.RefreshPolicy;
import org.springframework.data.elasticsearch.core.query.BulkOptions;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;

@Service
public class PostElasticsearchService {

    private final ElasticsearchOperations elasticsearchOperations;

    public PostElasticsearchService(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }


    public void bulkIndexPost(List<PostDocument> postDocuments) {
        List<IndexQuery> indexQueries = postDocuments.stream()
            .map(postDocument -> new IndexQueryBuilder()
                .withId(postDocument.getId().toString())
                .withObject(postDocument)
                .build())
            .toList();

        BulkOptions bulkOptions = BulkOptions.builder()
            .withTimeout(Duration.ofSeconds(3000))
            .withRefreshPolicy(RefreshPolicy.IMMEDIATE)
            .build();

        elasticsearchOperations.bulkIndex(indexQueries, bulkOptions, Post.class);
    }

    public long countAllPostDocuments() {
        NativeQuery query = NativeQuery.builder()
            .withQuery(q -> q.matchAll(m -> m))
            .build();

        return elasticsearchOperations.count(query, PostDocument.class);
    }

}
