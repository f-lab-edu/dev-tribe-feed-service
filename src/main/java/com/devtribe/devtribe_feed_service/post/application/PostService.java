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
        validateContentSource(request.contentSource());

        User findUser = userService.getUser(request.authorId());

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

    private void validateContentSource(ContentSource contentSource) {
        Preconditions.checkArgument(contentSource != null, "컨텐츠 소스는 필수값입니다.");
        Preconditions.checkArgument(contentSource.getContentId() != null, "컨텐츠 식별자는 필수값입니다.");
        Preconditions.checkArgument(contentSource.getContentType() != null, "컨텐츠 타입은 필수값입니다.");

        // TODO: ContentId를 가지는 ContentType에 해당하는 Source들이 존재하는지 검증 필요.
        //  ContentType에 맞는 서비스 필요. 커뮤니티, 채널 도메인 추가 후 작업 예정.
        //  ex) ChannelService.existById(contentId)
        //  ex) TribeService.existById(contentId)
    }
}
