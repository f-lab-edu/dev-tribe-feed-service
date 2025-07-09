package com.devtribe.domain.post.application;

import com.devtribe.domain.post.application.dtos.PostSearchRequest;
import com.devtribe.domain.post.application.dtos.PostSearchResponse;
import com.devtribe.domain.post.application.mapper.PostSearchMapper;
import com.devtribe.domain.post.application.validators.PostRequestValidator;
import com.devtribe.domain.post.dao.PostRepository;
import com.devtribe.domain.post.dto.PostSearchCriteria;
import com.devtribe.domain.post.entity.Post;
import com.devtribe.global.model.PageResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostSearchService {

    private final PostRepository postRepository;
    private final PostRequestValidator validator;
    private final PostSearchMapper postSearchMapper;

    public PostSearchService(
        PostRepository postRepository,
        PostRequestValidator validator,
        PostSearchMapper postSearchMapper
    ) {
        this.postRepository = postRepository;
        this.validator = validator;
        this.postSearchMapper = postSearchMapper;
    }

    @Transactional(readOnly = true)
    public PostSearchResponse searchPostByKeyword(PostSearchRequest request) {
        validator.validateSearchRequest(request);

        PostSearchCriteria criteria = postSearchMapper.toCriteria(request);
        PageResponse<Post> postSearchResult = postRepository.searchPostByKeyword(criteria);
        return postSearchMapper.toResponse(postSearchResult);
    }
}
