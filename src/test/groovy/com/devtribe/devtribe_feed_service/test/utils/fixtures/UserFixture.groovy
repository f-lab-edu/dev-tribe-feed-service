package com.devtribe.devtribe_feed_service.test.utils.fixtures

import com.devtribe.devtribe_feed_service.user.domain.User

import java.time.LocalDateTime

class UserFixture {

    static User createUser(Map map = [:]) {
        return new User(
                id: map.getOrDefault("id", null) as Long,
                email: map.getOrDefault("email", "example@gmail.com") as String,
                password: map.getOrDefault("password", "1q2w3e4r1!") as String,
                nickname: map.getOrDefault("nickname", "nickname") as String,
                biography: map.getOrDefault("biography", "biography") as String,
                companyName: map.getOrDefault("companyName", "devtribe-company") as String,
                jobTitle: map.getOrDefault("jobTitle", "backend") as String,
                githubUrl: map.getOrDefault("githubUrl", "github/devtribe") as String,
                linkedinUrl: map.getOrDefault("linkedinUrl", "linkedIn") as String,
                blogUrl: map.getOrDefault("blogUrl", "blog.com") as String,
                createdAt: map.getOrDefault("createdAt", LocalDateTime.now()) as LocalDateTime,
                updatedAt: map.getOrDefault("updatedAt", null) as LocalDateTime,
                createdBy: map.getOrDefault("createdBy", 1L) as Long,
                updatedBy: map.getOrDefault("updatedBy", null as Long)
        )
    }
}