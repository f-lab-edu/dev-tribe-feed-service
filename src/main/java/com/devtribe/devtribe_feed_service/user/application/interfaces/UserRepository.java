package com.devtribe.devtribe_feed_service.user.application.interfaces;

import com.devtribe.devtribe_feed_service.user.domain.User;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    boolean isEmailRegistered(String email);

    boolean isNicknameUsed(String nickname);

    Optional<User> findById(long id);
}
