package com.devtribe.unit.post.domain

import com.devtribe.domain.post.entity.Post
import com.devtribe.domain.post.entity.Publication
import com.devtribe.fixtures.post.domain.PostFixture
import spock.lang.Specification

import static com.devtribe.fixtures.user.domain.UserFixture.createUser

class PostTest extends Specification {

    def "게시글 제목, 본문, 썸네일, 공개여부 수정 성공"() {
        given:
        Post post = PostFixture.createPost(id:  1L)

        when:
        post.updatePostDetail("new title", "new content", "new thumbnail", Publication.PRIVATE)

        then:
        post.title == "new title"
        post.content == "new content"
        post.thumbnail == "new thumbnail"
        post.publication == Publication.PRIVATE
    }

    def "게시글 작성자 검증 성공"() {
        given:
        def post = PostFixture.createPost(id: 1L, userId: 1L)
        def requestUser = createUser(id: 1L)

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
        requestUser << [null, createUser(id: 2L)]
    }
}
