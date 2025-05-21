package com.devtribe.unit.post.application

import com.devtribe.domain.post.application.PostService
import com.devtribe.domain.post.application.validators.PostRequestValidator
import com.devtribe.domain.post.dao.PostJpaRepository
import com.devtribe.domain.post.entity.Post
import com.devtribe.domain.post.entity.Publication
import com.devtribe.domain.user.application.UserService
import com.devtribe.fixtures.post.domain.PostFixture
import spock.lang.Specification
import spock.lang.Subject

import static com.devtribe.fixtures.post.domain.PostFixture.getUpdatedPost
import static com.devtribe.fixtures.post.dto.CreatePostRequestFixture.getCreatePostRequest
import static com.devtribe.fixtures.post.dto.UpdatePostRequestFixture.getUpdatePostRequest
import static com.devtribe.fixtures.user.domain.UserFixture.createUser

class PostServiceTest extends Specification {

    def postRequestValidator = Mock(PostRequestValidator)
    def postRepository = Mock(PostJpaRepository)
    def userService = Mock(UserService)

    @Subject
    PostService postService = new PostService(postRequestValidator, postRepository, userService)

    def "이미 존재하는 postId가 주어질 때, postId를 가진 Post 반환에 성공한다."() {
        given:
        def postId = 1L
        def post = PostFixture.createPost(id: postId)
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
        def author = createUser(id: 1L)
        def request = getCreatePostRequest(1L)
        def savedPost = PostFixture.createPost(id:  1L)
        postRepository.save(_ as Post) >> savedPost
        userService.getUser(request.authorId()) >> author

        when:
        def actualPost = postService.createPost(request)

        then:
        actualPost.id() == savedPost.getId()
    }

    def "존재하지 않은 작성자로 Post 생성 요청이 주어질 때, Post 생성에 실패한다."() {
        given:
        def notExistUserId = 999L
        def request = getCreatePostRequest(notExistUserId)

        userService.getUser(request.authorId()) >> { throw new IllegalArgumentException("존재하지 않는 유저입니다.") }

        when:
        postService.createPost(request)

        then:
        def e = thrown(IllegalArgumentException)
        e.getMessage() == "존재하지 않는 유저입니다."

        and:
        0 * postRepository.save(_)
    }

    def "유효한 Post 수정 요청이 주어질 때, Post 수정에 성공한다."() {
        given:
        def postId = 1L
        def userId = 1L
        def author = createUser(id: userId)
        def updatePostRequest = getUpdatePostRequest(userId, Publication.PRIVATE)
        def originPost = PostFixture.createPost(id: postId, userId: userId)
        def expected = getUpdatedPost(postId, userId)

        and:
        postRepository.findById(postId) >> Optional.of(originPost)
        userService.getUser(updatePostRequest.authorId()) >> author
        postRepository.save(_ as Post) >> { args -> args[0] }

        when:
        def actual = postService.updatePost(postId, updatePostRequest)

        then:
        actual.title() == expected.getTitle()
        actual.content() == expected.getContent()
        actual.thumbnail() == expected.getThumbnail()
        actual.publication() == expected.getPublication()
    }

    def "작성자가 아닌 유저의 Post 수정 요청이 주어질 때, Post 수정에 실패한다."() {
        given:
        def postId = 1L

        def otherAuthorId = 2L
        def otherAuthor = createUser(id: otherAuthorId)
        def updatePostRequest = getUpdatePostRequest(otherAuthorId, Publication.PRIVATE)

        def authorId = 1L
        def originPost = PostFixture.createPost(id: postId, userId: authorId)

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
        def post = PostFixture.createPost(id:  postId)
        postRepository.findById(postId) >> Optional.of(post)

        when:
        postService.deletePost(postId)

        then:
        post.isDeleted
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
    }
}
