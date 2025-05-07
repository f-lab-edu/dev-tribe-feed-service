package com.devtribe.devtribe_feed_service.post.repository.jpa;

import com.devtribe.devtribe_feed_service.post.domain.vote.PostVote;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaVoteRepository extends JpaRepository<PostVote, Long> {

    Optional<PostVote> findByUserIdAndPostId(Long userId, Long postId);

}
