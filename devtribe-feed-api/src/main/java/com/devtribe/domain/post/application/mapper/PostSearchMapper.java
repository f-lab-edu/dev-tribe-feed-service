package com.devtribe.domain.post.application.mapper;

import com.devtribe.domain.post.application.dtos.PostResponse;
import com.devtribe.domain.post.application.dtos.PostSearchRequest;
import com.devtribe.domain.post.application.dtos.PostSearchResponse;
import com.devtribe.domain.post.dto.PostSearchCriteria;
import com.devtribe.domain.post.entity.Post;
import com.devtribe.global.model.PageRequest;
import com.devtribe.global.model.PageResponse;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PostSearchMapper {

    public PostSearchCriteria toCriteria(PostSearchRequest request) {
        PageRequest pageRequest = new PageRequest(request.page(), request.size());

        return new PostSearchCriteria(request.keyword(), pageRequest);
    }

    public PostSearchResponse toResponse(PageResponse<Post> pageResponse) {
        List<PostResponse> postResponses = convertToPostResponses(pageResponse.data());
        return new PostSearchResponse(postResponses);
    }

    private List<PostResponse> convertToPostResponses(List<Post> posts) {
        return posts.stream().map(PostResponse::from).toList();
    }
}
