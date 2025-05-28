package com.devtribe.domain.tag.api;

import com.devtribe.domain.tag.appliction.TagService;
import com.devtribe.domain.tag.appliction.dtos.TagCreateRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    public Long createTag(
        @RequestBody TagCreateRequest request
    ) {
        return tagService.createTag(request);
    }
}
