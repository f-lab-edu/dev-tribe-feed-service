package com.devtribe.domain.post.dao;

import com.devtribe.domain.post.entity.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostTagRepository extends JpaRepository<PostTag, Long>, PostTagRepositoryCustom{

    void deleteByPostIdAndTagId(Long postId, Long tagId);
}
