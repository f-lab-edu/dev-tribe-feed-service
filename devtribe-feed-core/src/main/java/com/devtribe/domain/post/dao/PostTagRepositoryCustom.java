package com.devtribe.domain.post.dao;

import com.devtribe.domain.tag.entity.Tag;
import java.util.List;

public interface PostTagRepositoryCustom {

    List<Tag> findAllTagByPostId(Long postId);
}
