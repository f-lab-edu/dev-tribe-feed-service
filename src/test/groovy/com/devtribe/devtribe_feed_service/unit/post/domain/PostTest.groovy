package com.devtribe.devtribe_feed_service.unit.post.domain

import com.devtribe.devtribe_feed_service.post.application.dtos.UpdatePostRequest
import com.devtribe.devtribe_feed_service.post.domain.Publication
import com.devtribe.devtribe_feed_service.test.utils.fixtures.post.PostFixture
import spock.lang.Specification

import static com.devtribe.devtribe_feed_service.test.utils.fixtures.UserFixture.getUser

class PostTest extends Specification {

    def "게시글 제목, 본문, 썸네일, 공개여부 수정 성공"() {
        given:
        def post = PostFixture.createPost(id:  1L)
        def request = new UpdatePostRequest(1L, "new title", "new content",
                "new thumbnail", Publication.PRIVATE)

        when:
        post.updatePostDetail(request)

        then:
        post.title == request.title()
        post.content == request.content()
        post.thumbnail == request.thumbnail()
        post.publication == request.publication()
    }

    def "게시글 작성자 검증 성공"() {
        given:
        def post = PostFixture.createPost(id: 1L, userId: 1L)
        def requestUser = getUser(1L)

        when:
        def isAuthor = post.isWrittenBy(requestUser)

        then:
        isAuthor
    }

    def "게시글 작성자 검증 실패"() {
        given:
        def post = PostFixture.createPost(id: 1L, userId: 1L)

        when:
        def isAuthor = post.isWrittenBy(requestUser)

        then:
        !isAuthor

        where:
        requestUser << [null, getUser(2L)]
    }
}
