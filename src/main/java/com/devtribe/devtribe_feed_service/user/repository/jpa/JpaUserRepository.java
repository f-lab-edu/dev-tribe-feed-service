package com.devtribe.devtribe_feed_service.user.repository.jpa;

import com.devtribe.devtribe_feed_service.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);
}
