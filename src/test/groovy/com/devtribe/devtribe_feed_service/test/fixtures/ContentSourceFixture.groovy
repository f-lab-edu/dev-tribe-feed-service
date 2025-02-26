package com.devtribe.devtribe_feed_service.test.fixtures

import com.devtribe.devtribe_feed_service.post.domain.ContentSource
import com.devtribe.devtribe_feed_service.post.domain.ContentType

class ContentSourceFixture {

    static ContentSource getUserContentSource(Long contentId) {
        return new ContentSource(contentId, ContentType.USER)
    }
}
