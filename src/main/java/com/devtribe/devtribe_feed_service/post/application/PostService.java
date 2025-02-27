package com.devtribe.devtribe_feed_service.post.application;

import com.devtribe.devtribe_feed_service.post.application.dtos.CreatePostRequest;
import com.devtribe.devtribe_feed_service.post.application.dtos.UpdatePostRequest;
import com.devtribe.devtribe_feed_service.post.application.interfaces.PostRepository;
import com.devtribe.devtribe_feed_service.post.application.validators.PostRequestValidator;
import com.devtribe.devtribe_feed_service.post.domain.ContentSource;
import com.devtribe.devtribe_feed_service.post.domain.Post;
import com.devtribe.devtribe_feed_service.user.application.UserService;
import com.devtribe.devtribe_feed_service.user.domain.User;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

    private final PostRequestValidator postRequestValidator;
    private final PostRepository postRepository;
    private final UserService userService;

    public PostService(PostRequestValidator postRequestValidator,
        PostRepository postRepository,
        UserService userService) {
        this.postRequestValidator = postRequestValidator;
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public Post getPost(Long postId) {
        return postRepository.findById(postId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다."));
    }

    @Transactional
    public Post createPost(CreatePostRequest request) {
        postRequestValidator.validateTitle(request.title());
        postRequestValidator.validateBody(request.content());
        postRequestValidator.validateContentSource(request.contentSource());

        User findUser = userService.getUser(request.authorId());
        // TODO 추가: ContentType별 Source 존재 여부 검증 로직
        //  - ContentId와 ContentType에 따른 Source 검증 로직
        //  - 향후 구현 예정:
        //   - ContentType별 전용 서비스 구현 (예: ChannelService, TribeService)
        //   - existById 메소드를 통한 존재 여부 확인
        //  - 커뮤니티, 채널 도메인 추가 후 구현 예정

        return postRepository.save(request.toEntity(findUser));
    }

    @Transactional
    public Post updatePost(Long postId, UpdatePostRequest request) {
        postRequestValidator.validateTitle(request.title());
        postRequestValidator.validateBody(request.content());

        Post findPost = getPost(postId);
        User requestAuthor = userService.getUser(request.authorId());
        validateAuthor(findPost, requestAuthor);

        findPost.updateTitle(request.title());
        findPost.updateContent(request.content());
        findPost.changeThumbnail(request.thumbnail());
        findPost.changePublication(request.publication());
        return postRepository.save(findPost);
    }

    public void deletePost(Long postId) {
        postRepository.delete(getPost(postId));
    }

    private void validateAuthor(Post findPost, User findAuthor) {
        Preconditions.checkArgument(findPost.getAuthor().equals(findAuthor), "게시물 작성자가 아닙니다.");
    }

}
