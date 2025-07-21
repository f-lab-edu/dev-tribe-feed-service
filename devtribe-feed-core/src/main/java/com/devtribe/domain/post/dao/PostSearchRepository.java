package com.devtribe.domain.post.dao;

import com.devtribe.domain.post.dto.PostSearchCriteria;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface PostSearchRepository {

    List<Long> searchPostByKeyword(PostSearchCriteria criteria);
}
