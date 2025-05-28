package com.devtribe.domain.post.dao;

import com.devtribe.domain.post.entity.PostTag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostTagRepository extends JpaRepository<PostTag, Long> {

    List<PostTag> findAllByPostId(Long postId);

    void deleteAllByPostId(Long postId);
}
