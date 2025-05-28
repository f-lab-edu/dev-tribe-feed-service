package com.devtribe.domain.post.application;

import com.devtribe.domain.post.application.validators.PostTagRequestValidator;
import com.devtribe.domain.post.dao.PostTagRepository;
import com.devtribe.domain.post.entity.PostTag;
import com.devtribe.domain.tag.appliction.TagService;
import com.devtribe.domain.tag.entity.Tag;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostTagService {

    private final PostTagRepository postTagRepository;
    private final PostTagRequestValidator validator;
    private final TagService tagService;

    public PostTagService(
        PostTagRepository postTagRepository,
        PostTagRequestValidator validator,
        TagService tagService
    ) {
        this.postTagRepository = postTagRepository;
        this.validator = validator;
        this.tagService = tagService;
    }

    @Transactional
    public void updatePostTag(Long postId, List<Long> requestTagIds) {
        validator.validateTagListSize(requestTagIds);

        List<Tag> originPostTags = postTagRepository.findAllTagByPostId(postId);
        List<Tag> requestTags = requestTagIds.stream().map(tagService::findTagById).toList();

        originPostTags.forEach(postTag -> removeIfNotMatched(postId, requestTags, postTag));
        requestTags.forEach(reqTag -> addIfNotMatched(postId, originPostTags, reqTag));
    }

    private void addIfNotMatched(Long postId, List<Tag> originPostTags, Tag reqTag) {
        if (originPostTags.stream().noneMatch(tag -> tag.hasSameId(reqTag))) {
            postTagRepository.save(new PostTag(postId, reqTag.getId()));
        }
    }

    private void removeIfNotMatched(Long postId, List<Tag> requestTags, Tag postTag) {
        if (requestTags.stream().noneMatch(tag -> tag.hasSameId(postTag))) {
            postTagRepository.deleteByPostIdAndTagId(postId, postTag.getId());
            postTagRepository.flush();
        }
    }

}
