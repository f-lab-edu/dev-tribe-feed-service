package com.devtribe.domain.post.application;

import com.devtribe.domain.post.application.dtos.CreatePostRequest;
import com.devtribe.domain.post.application.dtos.CreatePostResponse;
import com.devtribe.domain.post.application.dtos.UpdatePostRequest;
import com.devtribe.domain.post.application.dtos.UpdatePostResponse;
import com.devtribe.domain.post.application.validators.PostRequestValidator;
import com.devtribe.domain.post.dao.PostJpaRepository;
import com.devtribe.domain.post.dao.PostTagRepository;
import com.devtribe.domain.post.entity.Post;
import com.devtribe.domain.post.entity.PostTag;
import com.devtribe.domain.tag.dao.TagRepository;
import com.devtribe.domain.tag.entity.Tag;
import com.devtribe.domain.user.application.UserService;
import com.devtribe.domain.user.entity.User;
import com.google.common.base.Preconditions;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

    private final PostRequestValidator postRequestValidator;
    private final PostJpaRepository postRepository;
    private final PostTagRepository postTagRepository;
    private final TagRepository tagRepository;
    private final UserService userService;

    public PostService(
        PostRequestValidator postRequestValidator,
        PostJpaRepository postRepository,
        PostTagRepository postTagRepository,
        TagRepository tagRepository,
        UserService userService
    ) {
        this.postRequestValidator = postRequestValidator;
        this.postRepository = postRepository;
        this.postTagRepository = postTagRepository;
        this.tagRepository = tagRepository;
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    public Post getPost(Long postId) {
        return postRepository.findById(postId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다."));
    }

    @Transactional
    public CreatePostResponse createPost(CreatePostRequest request) {
        postRequestValidator.validateTitle(request.title());
        postRequestValidator.validateBody(request.content());

        User findUser = userService.getUser(request.authorId());
        Post savedPost = postRepository.save(request.toEntity(findUser));

        updatePostTag(savedPost.getId(), request.tags());
        return new CreatePostResponse(savedPost.getId());
    }

    @Transactional
    public UpdatePostResponse updatePost(Long postId, UpdatePostRequest request) {
        postRequestValidator.validateTitle(request.title());
        postRequestValidator.validateBody(request.content());

        Post findPost = getPost(postId);
        User requestAuthor = userService.getUser(request.authorId());
        validateAuthor(findPost, requestAuthor);

        findPost.updatePostDetail(request.title(), request.content(), request.thumbnail(), request.publication());
        updatePostTag(findPost.getId(), request.tags());
        return UpdatePostResponse.from(findPost);
    }

    @Transactional
    public void deletePost(Long postId) {
        Post findPost = getPost(postId);
        findPost.deletePost();
    }

    /**
     * TODO: 최적화 필요  간단 구현, 전체 delete 후 전체 insert 방식
     *
     */
    @Transactional
    public void updatePostTag(Long postId, List<Long> tagList) {
        postRequestValidator.validateTagListSize(tagList);

        List<Tag> existingTag = tagRepository.findAllById(tagList);

        postTagRepository.deleteAllByPostId(postId);
        postTagRepository.flush();

        List<PostTag> newPostTags = existingTag.stream()
            .map(tag -> new PostTag(postId, tag.getId()))
            .toList();
        postTagRepository.saveAll(newPostTags);
    }

    private void validateAuthor(Post findPost, User findAuthor) {
        Preconditions.checkArgument(findPost.isWrittenBy(findAuthor), "게시물 작성자가 아닙니다.");
    }

}
