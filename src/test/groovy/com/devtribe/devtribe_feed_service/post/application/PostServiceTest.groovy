package com.devtribe.devtribe_feed_service.post.application


import com.devtribe.devtribe_feed_service.post.application.interfaces.PostRepository
import com.devtribe.devtribe_feed_service.post.application.validators.PostRequestValidator
import com.devtribe.devtribe_feed_service.post.domain.ContentSource
import com.devtribe.devtribe_feed_service.post.domain.ContentType
import com.devtribe.devtribe_feed_service.post.domain.Post
import com.devtribe.devtribe_feed_service.post.domain.Publication
import com.devtribe.devtribe_feed_service.user.application.UserService
import spock.lang.Specification
import spock.lang.Subject

import static com.devtribe.devtribe_feed_service.test.fixtures.ContentSourceFixture.getUserContentSource
import static com.devtribe.devtribe_feed_service.test.fixtures.CreatePostRequestFixture.getCreatePostRequest
import static com.devtribe.devtribe_feed_service.test.fixtures.PostFixture.getPost
import static com.devtribe.devtribe_feed_service.test.fixtures.PostFixture.getUpdatedPost
import static com.devtribe.devtribe_feed_service.test.fixtures.UpdatePostRequestFixture.getUpdatePostRequest
import static com.devtribe.devtribe_feed_service.test.fixtures.UserFixture.getUser

class PostServiceTest extends Specification {

    def postRequestValidator = Mock(PostRequestValidator)
    def postRepository = Mock(PostRepository)
    def userService = Mock(UserService)

    @Subject
    PostService postService = new PostService(postRequestValidator, postRepository, userService)

    def "이미 존재하는 postId가 주어질 때, postId를 가진 Post 반환에 성공한다."() {
        given:
        def postId = 1L
        def post = getPost(postId)
        postRepository.findById(postId) >> Optional.of(post)

        when:
        def result = postService.getPost(postId)

        then:
        result.getId() == postId
    }

    def "존재하지 않는 postId가 주어질 때, postId를 가진 Post 반환에 실패한다."() {
        given:
        Long notExistPostId = 999L
        postRepository.findById(notExistPostId) >> Optional.empty()

        when:
        postService.getPost(notExistPostId)

        then:
        def e = thrown(IllegalArgumentException)
        e.getMessage() == "존재하지 않는 게시물입니다."
    }

    def "유효한 Post 생성 요청이 주어질 때, Post 생성에 성공한다."() {
        given:
        def contentSource = getUserContentSource(1L)
        def author = getUser(1L)
        def request = getCreatePostRequest(1L, contentSource)

        def expectedPost = request.toEntity(author)
        postRepository.save(_ as Post) >> { args -> args[0] }

        userService.getUser(request.authorId()) >> author

        when:
        def actualPost = postService.createPost(request)

        then:
        actualPost.getId() == expectedPost.getId()
        actualPost.getTitle() == expectedPost.getTitle()
        actualPost.getContent() == expectedPost.getContent()
        actualPost.getUserId() == author.getId()
        actualPost.getContentSource() == contentSource
    }

    def "존재하지 않은 작성자로 Post 생성 요청이 주어질 때, Post 생성에 실패한다."() {
        given:
        def notExistUserId = 999L
        def contentSource = getUserContentSource(1L)
        def request = getCreatePostRequest(notExistUserId, contentSource)

        userService.getUser(request.authorId()) >> { throw new IllegalArgumentException("존재하지 않는 유저입니다.") }

        when:
        postService.createPost(request)

        then:
        def e = thrown(IllegalArgumentException)
        e.getMessage() == "존재하지 않는 유저입니다."

        and:
        0 * postRepository.save(_)
    }

    def "contentSource가 null로 Post 생성 요청이 주어질 때, Post 생성에 실패한다."() {
        given:
        def request = getCreatePostRequest(1L, null)

        when:
        postService.createPost(request)

        then:
        def e = thrown(IllegalArgumentException)
        e.getMessage() == "컨텐츠 소스는 필수값입니다."
    }

    def "유효하지 않은 contentSource로 Post 생성 요청이 주어질 때, Post 생성에 실패한다."() {
        given:
        def invalidContentSource = new ContentSource(contentId, contentType)
        def request = getCreatePostRequest(1L, invalidContentSource)

        when:
        postService.createPost(request)

        then:
        def e = thrown(IllegalArgumentException)
        e.getMessage() == expectedMessage

        where:
        contentId | contentType      || expectedMessage
        null      | ContentType.USER || "컨텐츠 식별자는 필수값입니다."
        1L        | null             || "컨텐츠 타입은 필수값입니다."
    }

    def "유효한 Post 수정 요청이 주어질 때, Post 수정에 성공한다."() {
        given:
        def postId = 1L
        def userId = 1L
        def author = getUser(userId)
        def contentSource = getUserContentSource(userId)
        def updatePostRequest = getUpdatePostRequest(userId, Publication.PRIVATE)
        def originPost = getPost(postId, userId, contentSource)
        def expected = getUpdatedPost(postId, userId, contentSource)

        and:
        postRepository.findById(postId) >> Optional.of(originPost)
        userService.getUser(updatePostRequest.authorId()) >> author
        postRepository.save(_ as Post) >> { args -> args[0] }

        when:
        def actual = postService.updatePost(postId, updatePostRequest)

        then:
        actual.getId() == expected.getId()
        actual.getTitle() == expected.getTitle()
        actual.getContent() == expected.getContent()
        actual.getThumbnail() == expected.getThumbnail()
        actual.getPublication() == expected.getPublication()
    }

    def "작성자가 아닌 유저의 Post 수정 요청이 주어질 때, Post 수정에 실패한다."() {
        given:
        def postId = 1L

        def otherAuthorId = 2L
        def otherAuthor = getUser(otherAuthorId)
        def updatePostRequest = getUpdatePostRequest(otherAuthorId, Publication.PRIVATE)

        def authorId = 1L
        def originPost = getPost(postId, authorId, getUserContentSource(authorId))

        and:
        postRepository.findById(postId) >> Optional.of(originPost)
        userService.getUser(updatePostRequest.authorId()) >> otherAuthor

        when:
        postService.updatePost(postId, updatePostRequest)

        then:
        def e = thrown(IllegalArgumentException)
        e.getMessage() == "게시물 작성자가 아닙니다."
    }

    def "이미 존재하는 postId가 주어질때, postId를 가진 Post 삭제에 성공한다."() {
        given:
        def postId = 1L
        def post = getPost(postId)
        postRepository.findById(postId) >> Optional.of(post)

        when:
        postService.deletePost(postId)

        then:
        1 * postRepository.delete(post)
    }

    def "존재하지 않는 postId가 주어질때, postId를 가진 Post 삭제에 실패한다."() {
        given:
        def notExistPostId = 999L
        postRepository.findById(notExistPostId) >> { throw new IllegalArgumentException("존재하지 않는 게시물입니다.") }

        when:
        postService.deletePost(notExistPostId)

        then:
        def e = thrown(IllegalArgumentException)
        e.getMessage() == "존재하지 않는 게시물입니다."

        and:
        0 * postRepository.delete(_)
    }
}
