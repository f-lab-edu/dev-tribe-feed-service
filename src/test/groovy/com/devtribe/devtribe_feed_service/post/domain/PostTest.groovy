package com.devtribe.devtribe_feed_service.post.domain

import com.devtribe.devtribe_feed_service.post.application.dtos.UpdatePostRequest
import spock.lang.Specification

import static com.devtribe.devtribe_feed_service.test.fixtures.PostFixture.getPost
import static com.devtribe.devtribe_feed_service.test.fixtures.UserFixture.getUser

class PostTest extends Specification {

    def "게시글 제목, 본문, 썸네일, 공개여부 수정 성공"() {
        given:
        def post = getPost(1L)
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
        def author = 1L
        def post = getPost(1L, author)
        def requestUser = getUser(1L)

        when:
        def isAuthor = post.isWrittenBy(requestUser)

        then:
        isAuthor
    }

    def "게시글 작성자 검증 실패"() {
        given:
        def author = 1L
        def post = getPost(1L, author)

        when:
        def isAuthor = post.isWrittenBy(requestUser)

        then:
        !isAuthor

        where:
        requestUser << [null, getUser(2L)]
    }
}
