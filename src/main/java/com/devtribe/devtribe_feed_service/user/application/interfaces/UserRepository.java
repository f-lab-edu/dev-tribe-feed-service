package com.devtribe.devtribe_feed_service.user.application.interfaces;

import com.devtribe.devtribe_feed_service.user.domain.User;

public interface UserRepository {

    User save(User user);
}
