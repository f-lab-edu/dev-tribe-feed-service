package com.devtribe.devtribe_feed_service.post.application.interfaces;

import com.devtribe.devtribe_feed_service.post.domain.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository {

    Post save(Post post);

    List<Post> saveAll(List<Post> posts);

    Optional<Post> findById(Long postId);
}
