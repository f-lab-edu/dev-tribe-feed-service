package com.devtribe.step;

import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOptionsBuilders;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchAllQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.batch.item.ItemReader;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;

/**
 * Elasticsearch SearchAfter 방식의 데이터 Reader. 이전 페이지의 마지막 문서의 정렬 기준 값으로 데이터를 가져오는 방식.
 *
 * @param <T>
 */
public class ElasticsearchSearchAfterItemReader<T> implements ItemReader<T> {

    private final ElasticsearchOperations elasticsearchOperations;
    private final String indexName;
    private final int batchSize;
    private final Class<T> targetClass;
    private final String sortField;

    private List<T> currentItems = new ArrayList<>();
    private boolean hasNext = true;
    private List<Object> searchAfterValues = null;
    private int currentIndex = 0;

    public ElasticsearchSearchAfterItemReader(
        ElasticsearchOperations elasticsearchOperations,
        String indexName,
        int batchSize, Class<T> targetClass,
        String sortField
    ) {
        this.elasticsearchOperations = elasticsearchOperations;
        this.indexName = indexName;
        this.batchSize = batchSize;
        this.targetClass = targetClass;
        this.sortField = sortField;
    }

    @Override
    public T read() {
        if (currentIndex >= currentItems.size()) {
            fetchNextBatch();
        }

        if (currentIndex < currentItems.size()){
            T t = currentItems.get(currentIndex++);
            return t;
        }

        return null;
    }

    private void fetchNextBatch() {
        if (!hasNext) {
            return;
        }

        NativeQueryBuilder queryBuilder = new NativeQueryBuilder()
            .withQuery(MatchAllQuery.of(f -> f)._toQuery())
            .withSort(getSortOptions())
            .withPageable(PageRequest.of(0, batchSize));

        if (searchAfterValues != null) {
            queryBuilder.withSearchAfter(searchAfterValues);
        }

        NativeQuery query = queryBuilder.build();

        SearchHits<T> searchHits = elasticsearchOperations.search(
            query,
            targetClass,
            IndexCoordinates.of(indexName)
        );

        currentItems = searchHits.getSearchHits().stream()
            .map(SearchHit::getContent)
            .collect(Collectors.toList());
        currentIndex = 0;

        setSearchAfterValues(searchHits);
    }

    private void setSearchAfterValues(SearchHits<T> searchHits) {
        if (searchHits.isEmpty()) {
            hasNext = false;
        } else {
            SearchHit<T> lastHit = searchHits.getSearchHits().get(
                searchHits.getSearchHits().size() - 1);
            searchAfterValues = lastHit.getSortValues();
        }
    }

    private SortOptions getSortOptions() {
        return SortOptionsBuilders.field(field -> field.field(sortField).order(SortOrder.Desc));
    }
}
