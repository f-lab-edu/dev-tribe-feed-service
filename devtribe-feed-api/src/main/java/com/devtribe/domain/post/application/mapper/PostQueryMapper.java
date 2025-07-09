package com.devtribe.domain.post.application.mapper;

import com.devtribe.domain.post.application.dtos.PostQueryRequest;
import com.devtribe.domain.post.application.dtos.PostQueryResponse;
import com.devtribe.domain.post.application.dtos.PostResponse;
import com.devtribe.domain.post.dto.PostFilterCriteria;
import com.devtribe.domain.post.dto.PostQueryCriteria;
import com.devtribe.domain.post.entity.Post;
import com.devtribe.global.model.PageRequest;
import com.devtribe.global.model.PageResponse;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PostQueryMapper {

    public PostQueryCriteria toCriteria(PostQueryRequest request) {
        PostFilterCriteria filterCriteria = new PostFilterCriteria(
            request.startDate(),
            request.endDate(),
            request.authorId()
        );

        PageRequest pageRequest = new PageRequest(request.page(), request.size());

        return new PostQueryCriteria(filterCriteria, request.sort(), pageRequest);
    }

    public PostQueryResponse toResponse(PageResponse<Post> pageResponse) {
        List<PostResponse> postResponses = convertToPostResponses(pageResponse.data());
        return new PostQueryResponse(postResponses, pageResponse.pageNo());
    }

    private List<PostResponse> convertToPostResponses(List<Post> posts) {
        return posts.stream().map(PostResponse::from).toList();
    }
}
