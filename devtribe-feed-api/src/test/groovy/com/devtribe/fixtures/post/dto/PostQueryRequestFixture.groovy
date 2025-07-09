package com.devtribe.fixtures.post.dto

import com.devtribe.domain.post.application.dtos.PostQueryRequest
import com.devtribe.domain.post.dto.PostSortCriteria

import java.time.LocalDateTime

class PostQueryRequestFixture {

    static PostQueryRequest createPostQueryRequest(Map map = [:]) {

        def startDate = map.getOrDefault("startDate", LocalDateTime.now()) as LocalDateTime
        def endDate = map.getOrDefault("endDate", LocalDateTime.now().plusMonths(1L)) as LocalDateTime
        def authorId = map.getOrDefault("authorId", 1L) as Long
        def sort = map.getOrDefault("sort", PostSortCriteria.NEWEST) as PostSortCriteria
        def page = map.getOrDefault("page", 0) as Integer
        def size = map.getOrDefault("size", 10) as Integer

        new PostQueryRequest(
                startDate,
                endDate,
                authorId,
                sort,
                page,
                size,
        )
    }
}
