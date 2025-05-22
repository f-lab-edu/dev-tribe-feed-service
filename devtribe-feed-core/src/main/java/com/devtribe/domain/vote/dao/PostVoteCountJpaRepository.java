package com.devtribe.domain.vote.dao;

import com.devtribe.domain.vote.entity.PostVoteCount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostVoteCountJpaRepository extends JpaRepository<PostVoteCount, Long> {

}
