package com.devtribe.domain.post.application.validators;

import com.devtribe.domain.post.dto.PostFilterCriteria;
import com.devtribe.domain.post.dto.PostSortCriteria;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Component;

@Component
public class FeedRequestValidator {

    public static final int MAX_PAGE_SIZE = 30;

    public void validateSortOption(PostSortCriteria sort) {
        Preconditions.checkArgument(sort != null, "유효하지 않은 요청 값입니다.");
    }

    public void validateFilterOption(PostFilterCriteria postFilterCriteria) {
        Preconditions.checkArgument(postFilterCriteria != null, "유효하지 않은 요청 값입니다.");

        Preconditions.checkArgument(
            postFilterCriteria.getAuthorId() == null ||
            postFilterCriteria.getAuthorId() > 0,
            "작성자가 유효하지 않습니다."
        );

        if (postFilterCriteria.getStartDate() == null && postFilterCriteria.getEndDate() == null) {
            return;
        }

        Preconditions.checkArgument(
            postFilterCriteria.getStartDate() != null && postFilterCriteria.getEndDate() != null,
            "날짜 범위가 유효하지 않습니다."
        );

        Preconditions.checkArgument(
            !postFilterCriteria.getStartDate().isAfter(postFilterCriteria.getEndDate()),
            "시작 날짜는 종료 날짜보다 이전이어야 합니다."
        );
    }

    public void validatePagination(int size) {
        Preconditions.checkArgument(size <= MAX_PAGE_SIZE, "요청한 페이지 수의 범위가 올바르지 않습니다.");
    }
}
