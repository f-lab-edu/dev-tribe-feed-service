package com.devtribe.domain.post.dao;

import com.devtribe.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostSearchRepository {

}
