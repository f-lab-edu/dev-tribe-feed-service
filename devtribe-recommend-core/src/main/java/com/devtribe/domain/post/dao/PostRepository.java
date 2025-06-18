package com.devtribe.domain.post.dao;

import com.devtribe.domain.post.entity.PostDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PostRepository extends ElasticsearchRepository<PostDocument, Long> {

}
