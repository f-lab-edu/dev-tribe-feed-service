package com.devtribe.domain.post.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devtribe.domain.post.application.dtos.CreatePostRequest;
import com.devtribe.domain.post.application.dtos.CreatePostResponse;
import com.devtribe.domain.post.application.dtos.PostDetailResponse;
import com.devtribe.domain.post.application.dtos.PostQueryRequest;
import com.devtribe.domain.post.application.dtos.PostQueryResponse;
import com.devtribe.domain.post.application.dtos.UpdatePostRequest;
import com.devtribe.domain.post.application.dtos.UpdatePostResponse;
import com.devtribe.domain.post.application.mapper.PostQueryMapper;
import com.devtribe.domain.post.application.validators.PostRequestValidator;
import com.devtribe.domain.post.dao.PostRepository;
import com.devtribe.domain.post.dto.PostQueryCriteria;
import com.devtribe.domain.post.entity.Post;
import com.devtribe.domain.tag.appliction.dtos.TagResponse;
import com.devtribe.domain.user.entity.User;
import com.devtribe.global.security.CustomUserDetail;
import com.devtribe.global.model.PageResponse;

import com.google.common.base.Preconditions;

@Service
public class PostService {

    private final PostRequestValidator postRequestValidator;
    private final PostRepository postRepository;
    private final PostTagService postTagService;
    private final PostQueryMapper postQueryMapper;

    public PostService(
        PostRequestValidator postRequestValidator,
        PostRepository postRepository,
        PostTagService postTagService,
        PostQueryMapper postQueryMapper
    ) {
        this.postRequestValidator = postRequestValidator;
        this.postRepository = postRepository;
        this.postTagService = postTagService;
        this.postQueryMapper = postQueryMapper;
    }

    @Transactional(readOnly = true)
    public Post getPost(Long postId) {
        return postRepository.findById(postId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다."));
    }

    @Transactional(readOnly = true)
    public PostQueryResponse getPostList(PostQueryRequest request) {
        postRequestValidator.validateQueryRequest(request);

        PostQueryCriteria criteria = postQueryMapper.toCriteria(request);
        PageResponse<Post> postPageResult = postRepository.findPostsByCriteria(criteria);

        return postQueryMapper.toResponse(postPageResult);
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
