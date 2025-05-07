package com.devtribe.devtribe_feed_service.post.repository.jpa;

import com.devtribe.devtribe_feed_service.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface JpaPostRepository extends JpaRepository<Post, Long> {

    @Modifying
    @Query(value = "UPDATE Post p "
        + "SET p.upvoteCount = p.upvoteCount + 1 ,"
        + "p.downvoteCount = CASE WHEN p.downvoteCount > 0 THEN p.downvoteCount - 1 ELSE 0 END, "
        + "p.updatedAt = now() "
        + "WHERE p.id = :id")
    void increaseUpvoteCount(Long id);

    @Modifying
    @Query(value = "UPDATE Post p "
        + "SET p.upvoteCount = CASE WHEN p.upvoteCount > 0 THEN p.upvoteCount - 1 ELSE 0 END,"
        + "p.downvoteCount = p.downvoteCount + 1,"
        + "p.updatedAt = now() "
        + "WHERE p.id = :id")
    void increaseDownvoteCount(Long id);

}
