package com.devtribe.domain.user.dao;

import com.devtribe.domain.user.entity.User;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    boolean isEmailRegistered(String email);

    boolean isNicknameUsed(String nickname);

    Optional<User> findById(long id);

    Optional<User> findByEmail(String email);
}
