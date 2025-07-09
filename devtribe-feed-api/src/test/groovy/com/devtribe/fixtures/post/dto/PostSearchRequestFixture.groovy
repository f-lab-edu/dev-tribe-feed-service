package com.devtribe.fixtures.post.dto


import com.devtribe.domain.post.application.dtos.PostSearchRequest

class PostSearchRequestFixture {

    static PostSearchRequest createPostQueryRequest(Map map = [:]) {

        def keyword = map.getOrDefault("keyword", "스프링") as String
        def page = map.getOrDefault("page", 0) as Integer
        def size = map.getOrDefault("size", 10) as Integer

        new PostSearchRequest(
                keyword,
                page,
                size,
        )
    }
}
