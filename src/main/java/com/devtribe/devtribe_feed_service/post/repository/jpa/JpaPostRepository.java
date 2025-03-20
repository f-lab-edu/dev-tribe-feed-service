package com.devtribe.devtribe_feed_service.post.repository.jpa;

import com.devtribe.devtribe_feed_service.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPostRepository extends JpaRepository<Post, Long> {

}
