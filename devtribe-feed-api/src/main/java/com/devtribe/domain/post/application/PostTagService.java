package com.devtribe.domain.post.application;

import com.devtribe.domain.post.application.validators.PostTagRequestValidator;
import com.devtribe.domain.post.dao.PostTagRepository;
import com.devtribe.domain.post.entity.PostTag;
import com.devtribe.domain.tag.dao.TagRepository;
import com.devtribe.domain.tag.entity.Tag;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostTagService {

    private final PostTagRepository postTagRepository;
    private final TagRepository tagRepository;
    private final PostTagRequestValidator validator;

    public PostTagService(
        PostTagRepository postTagRepository,
        TagRepository tagRepository,
        PostTagRequestValidator validator
    ) {
        this.postTagRepository = postTagRepository;
        this.tagRepository = tagRepository;
        this.validator = validator;
    }

    /**
     * TODO: 최적화 필요  간단 구현, 전체 delete 후 전체 insert 방식
     *
     */
    @Transactional
    public void updatePostTag(Long postId, List<Long> tagList) {
        validator.validateTagListSize(tagList);

        List<Tag> existingTag = tagRepository.findAllById(tagList);

        postTagRepository.deleteAllByPostId(postId);
        postTagRepository.flush();

        List<PostTag> newPostTags = existingTag.stream()
            .map(tag -> new PostTag(postId, tag.getId()))
            .toList();
        postTagRepository.saveAll(newPostTags);
    }

}
