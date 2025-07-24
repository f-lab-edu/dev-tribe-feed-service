package com.devtribe.devtribefeedbatch.service.post;

import com.devtribe.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostBatchRepository extends JpaRepository<Post, Long> {

}
