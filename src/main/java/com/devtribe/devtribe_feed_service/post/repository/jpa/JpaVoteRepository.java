package com.devtribe.devtribe_feed_service.post.repository.jpa;

import com.devtribe.devtribe_feed_service.post.domain.vote.PostVote;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface JpaVoteRepository extends JpaRepository<PostVote, Long> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    Optional<PostVote> findByUserIdAndPostId(Long userId, Long postId);

}
