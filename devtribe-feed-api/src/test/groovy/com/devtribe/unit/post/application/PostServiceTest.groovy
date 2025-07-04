package com.devtribe.unit.post.application

import com.devtribe.domain.post.application.PostService
import com.devtribe.domain.post.application.PostTagService
import com.devtribe.domain.post.application.validators.PostRequestValidator
import com.devtribe.domain.post.dao.PostRepository
import com.devtribe.domain.post.entity.Post
import com.devtribe.domain.post.entity.Publication
import com.devtribe.fixtures.post.domain.PostFixture
import com.devtribe.global.security.CustomUserDetail
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

import static com.devtribe.fixtures.post.domain.PostFixture.getUpdatedPost
import static com.devtribe.fixtures.post.dto.CreatePostRequestFixture.createPostRequest
import static com.devtribe.fixtures.post.dto.UpdatePostRequestFixture.getUpdatePostRequest
import static com.devtribe.fixtures.user.domain.UserFixture.createUser

@Title("게시글 서비스 테스트")
class PostServiceTest extends Specification {

    def postRequestValidator = Mock(PostRequestValidator)
    def postRepository = Mock(PostRepository)
    def userService = Mock(UserService)
    def postTagService = Mock(PostTagService)

    @Subject
    PostService postService = new PostService(
            postRequestValidator,
            postRepository,
            postTagService
    )

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
        def userDetail = new CustomUserDetail(createUser(id: 1L))
        def request = createPostRequest()
        def savedPost = PostFixture.createPost(id: 1L)
        postRepository.save(_ as Post) >> savedPost

        when:
        def actualPost = postService.createPost(request, userDetail)

        then:
        actualPost.id() == savedPost.getId()
    }

    def "유효한 Post 수정 요청이 주어질 때, Post 수정에 성공한다."() {
        given:
        def postId = 1L
        def userId = 1L
        def userDetail = new CustomUserDetail(createUser(id: 1L))
        def updatePostRequest = getUpdatePostRequest(Publication.PRIVATE)
        def originPost = PostFixture.createPost(id: postId, userId: userId)
        def expected = getUpdatedPost(postId, userId)

        and:
        postRepository.findById(postId) >> Optional.of(originPost)
        postRepository.save(_ as Post) >> { args -> args[0] }

        when:
        def actual = postService.updatePost(postId, updatePostRequest, userDetail)

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
        def userDetail = new CustomUserDetail(createUser(id: otherAuthorId))
        def updatePostRequest = getUpdatePostRequest(Publication.PRIVATE)

        def authorId = 1L
        def originPost = PostFixture.createPost(id: postId, userId: authorId)

        and:
        postRepository.findById(postId) >> Optional.of(originPost)

        when:
        postService.updatePost(postId, updatePostRequest, userDetail)

        then:
        def e = thrown(IllegalArgumentException)
        e.getMessage() == "게시물 작성자가 아닙니다."
    }

    def "이미 존재하는 postId가 주어질때, postId를 가진 Post 삭제에 성공한다."() {
        given:
        def postId = 1L
        def post = PostFixture.createPost(id: postId)
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
