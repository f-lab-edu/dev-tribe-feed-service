package com.devtribe.domain.post.dao;

import com.devtribe.domain.post.dto.PostQueryCriteria;
import com.devtribe.domain.post.entity.Post;
import com.devtribe.global.model.PageResponse;
import org.springframework.stereotype.Repository;

@Repository
public interface PostQueryRepository {

    PageResponse<Post> findPostsByCriteria(PostQueryCriteria postQueryCriteria);
}
