package com.devtribe.domain.post.dao;

import com.devtribe.domain.post.entity.PostClickLogDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PostClickLogRepository extends ElasticsearchRepository<PostClickLogDocument, Long> {

}
