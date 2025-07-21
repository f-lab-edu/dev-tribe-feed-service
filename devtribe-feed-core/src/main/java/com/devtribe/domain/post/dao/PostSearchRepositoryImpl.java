package com.devtribe.domain.post.dao;

import com.devtribe.domain.post.dto.PostSearchCriteria;
import com.devtribe.domain.post.entity.PostDocument;
import java.util.List;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Repository;

@Repository
public class PostSearchRepositoryImpl implements PostSearchRepository {

    private final ElasticsearchOperations operations;

    public PostSearchRepositoryImpl(ElasticsearchOperations operations) {
        this.operations = operations;
    }

    @Override
    public List<Long> searchPostByKeyword(PostSearchCriteria criteria) {

        NativeQuery query = NativeQuery.builder()
            .withQuery(
                q -> q.multiMatch(
                        m -> m
                            .query(criteria.keyword())
                            .fields("title", "content")))
            .build();

        SearchHits<PostDocument> search = operations.search(query, PostDocument.class);
        return search
            .stream()
            .map(searchHit -> searchHit.getContent().getId())
            .toList();
    }
}
