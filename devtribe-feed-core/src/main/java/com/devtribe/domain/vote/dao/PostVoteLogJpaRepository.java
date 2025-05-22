package com.devtribe.domain.vote.dao;

import com.devtribe.domain.vote.entity.PostVoteLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostVoteLogJpaRepository extends JpaRepository<PostVoteLog, Long> {

}
