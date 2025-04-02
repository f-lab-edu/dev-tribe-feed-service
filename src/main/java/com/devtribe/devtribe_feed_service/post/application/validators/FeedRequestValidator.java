package com.devtribe.devtribe_feed_service.post.application.validators;

import com.devtribe.devtribe_feed_service.post.domain.FeedFilterOption;
import com.devtribe.devtribe_feed_service.post.domain.FeedSortOption;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Component;

@Component
public class FeedRequestValidator {

    public static final int MAX_KEYWORD_LENGTH = 20;
    public static final int MAX_PAGE_SIZE = 30;

    public void validateSortOption(FeedSortOption sort) {
        Preconditions.checkArgument(sort != null, "유효하지 않은 요청 값입니다.");
    }

    public void validateFilterOption(FeedFilterOption filterOption) {
        Preconditions.checkArgument(filterOption != null, "유효하지 않은 요청 값입니다.");

        Preconditions.checkArgument(
            filterOption.getKeyword() == null ||
                filterOption.getKeyword().length() <= MAX_KEYWORD_LENGTH,
            "최대 키워드 길이 " + MAX_KEYWORD_LENGTH + "자를 넘을 수 없습니다."
        );

        Preconditions.checkArgument(
            filterOption.getAuthorId() == null ||
            filterOption.getAuthorId() > 0,
            "작성자가 유효하지 않습니다."
        );

        if (filterOption.getStartDate() == null && filterOption.getEndDate() == null) {
            return;
        }

        Preconditions.checkArgument(
            filterOption.getStartDate() != null && filterOption.getEndDate() != null,
            "날짜 범위가 유효하지 않습니다."
        );

        Preconditions.checkArgument(
            !filterOption.getStartDate().isAfter(filterOption.getEndDate()),
            "시작 날짜는 종료 날짜보다 이전이어야 합니다."
        );
    }

    public void validatePagination(int size) {
        Preconditions.checkArgument(size <= MAX_PAGE_SIZE, "요청한 페이지 수의 범위가 올바르지 않습니다.");
    }
}
