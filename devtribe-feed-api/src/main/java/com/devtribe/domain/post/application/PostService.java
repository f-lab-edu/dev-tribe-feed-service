package com.devtribe.domain.post.application;

import com.devtribe.domain.post.application.dtos.CreatePostRequest;
import com.devtribe.domain.post.application.dtos.CreatePostResponse;
import com.devtribe.domain.post.application.dtos.PostDetailResponse;
import com.devtribe.domain.post.application.dtos.UpdatePostRequest;
import com.devtribe.domain.post.application.dtos.UpdatePostResponse;
import com.devtribe.domain.post.application.validators.PostRequestValidator;
import com.devtribe.domain.post.dao.PostJpaRepository;
import com.devtribe.domain.post.entity.Post;
import com.devtribe.domain.tag.appliction.dtos.TagResponse;
import com.devtribe.domain.user.entity.User;
import com.devtribe.global.security.CustomUserDetail;
import com.google.common.base.Preconditions;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

    private final PostRequestValidator postRequestValidator;
    private final PostJpaRepository postRepository;
    private final PostTagService postTagService;

    public PostService(
        PostRequestValidator postRequestValidator,
        PostJpaRepository postRepository,
        PostTagService postTagService
    ) {
        this.postRequestValidator = postRequestValidator;
        this.postRepository = postRepository;
        this.postTagService = postTagService;
    }

    @Transactional(readOnly = true)
    public Post getPost(Long postId) {
        return postRepository.findById(postId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다."));
    }

    @Transactional
    public CreatePostResponse createPost(CreatePostRequest request, CustomUserDetail userDetail) {
        postRequestValidator.validateTitle(request.title());
        postRequestValidator.validateBody(request.content());

        User findUser = userDetail.getUser();
        Post savedPost = postRepository.save(request.toEntity(findUser));

        postTagService.updatePostTag(savedPost.getId(), request.tags());
        return new CreatePostResponse(savedPost.getId());
    }

    @Transactional(readOnly = true)
    public PostDetailResponse getPostDetail(Long postId) {
        Post findPost = getPost(postId);
        List<TagResponse> postTags = postTagService.getTagsByPostId(postId);
        return PostDetailResponse.of(findPost, postTags);
    }

    @Transactional
    public UpdatePostResponse updatePost(
        Long postId,
        UpdatePostRequest request,
        CustomUserDetail userDetail
    ) {
        postRequestValidator.validateTitle(request.title());
        postRequestValidator.validateBody(request.content());

        Post findPost = getPost(postId);
        User user = userDetail.getUser();
        validateAuthor(findPost, user);

        findPost.updatePostDetail(request.title(), request.content(), request.thumbnail(), request.publication());
        postTagService.updatePostTag(findPost.getId(), request.tags());
        return UpdatePostResponse.from(findPost);
    }

    @Transactional
    public void deletePost(Long postId) {
        Post findPost = getPost(postId);
        findPost.deletePost();
    }

    private void validateAuthor(Post findPost, User findAuthor) {
        Preconditions.checkArgument(findPost.isWrittenBy(findAuthor), "게시물 작성자가 아닙니다.");
    }

}
