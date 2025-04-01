package com.devtribe.devtribe_feed_service.user.repository;

import com.devtribe.devtribe_feed_service.user.application.interfaces.UserRepository;
import com.devtribe.devtribe_feed_service.user.domain.User;
import com.devtribe.devtribe_feed_service.user.repository.jpa.JpaUserRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    public UserRepositoryImpl(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public User save(User user) {
        return jpaUserRepository.save(user);
    }

    @Override
    public boolean isEmailRegistered(String email) {
        return jpaUserRepository.existsByEmail(email);
    }

    @Override
    public boolean isNicknameUsed(String nickname) {
        return jpaUserRepository.existsByNickname(nickname);
    }

    @Override
    public Optional<User> findById(long id) {
        return jpaUserRepository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email);
    }
}
