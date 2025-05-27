package com.devtribe.fixtures.tag.domain

import com.devtribe.domain.tag.entity.Tag

import java.time.LocalDateTime

class TagFixture {
    static Tag createTag(Map map = [:]) {
        new Tag(
                id: map.getOrDefault("id", null) as Long,
                name: map.getOrDefault("name", "name") as String,
                createdAt: map.getOrDefault("createdAt", LocalDateTime.now()) as LocalDateTime,
                updatedAt: map.getOrDefault("updatedAt", null) as LocalDateTime,
                createdBy: map.getOrDefault("createdBy", 1L) as Long,
                updatedBy: map.getOrDefault("updatedBy", null as Long)
        )
    }

}
