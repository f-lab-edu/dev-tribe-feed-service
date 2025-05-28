package com.devtribe.domain.tag.appliction;

import com.devtribe.domain.tag.appliction.dtos.TagCreateRequest;
import com.devtribe.domain.tag.appliction.dtos.TagResponse;
import com.devtribe.domain.tag.appliction.validators.TagRequestValidator;
import com.devtribe.domain.tag.dao.TagRepository;
import com.devtribe.domain.tag.entity.Tag;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TagService {

    private final TagRepository tagRepository;
    private final TagRequestValidator validator;

    public TagService(
        TagRepository tagRepository,
        TagRequestValidator validator
    ) {
        this.tagRepository = tagRepository;
        this.validator = validator;
    }

    @Transactional
    public Long createTag(TagCreateRequest request) {
        validator.validateTagNameLength(request.tagName());
        return tagRepository.save(new Tag(request.tagName())).getId();
    }

    @Transactional(readOnly = true)
    public Tag findTagById(Long tagId) {
        return tagRepository.findById(tagId)
            .orElseThrow(() -> new IllegalArgumentException("요청한 태그가 존재하지 않습니다."));
    }

    @Transactional(readOnly = true)
    public List<TagResponse> getTags() {
        return tagRepository.findAll().stream().map(TagResponse::from).toList();
    }
}
