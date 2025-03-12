package com.devtribe.devtribe_feed_service.post.domain

import spock.lang.Specification

class FeedSortOptionTest extends Specification {

    def"null 값이 주어지면 최신순이 나와야한다."(){
        given:
        String value = null

        when:
        def feedSortOption = FeedSortOption.fromValue(value)

        then:
        FeedSortOption.BY_NEWEST == feedSortOption
    }
}
