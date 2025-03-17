package com.devtribe.devtribe_feed_service.post.application.validators;

import com.devtribe.devtribe_feed_service.global.common.CursorPagination;
import com.devtribe.devtribe_feed_service.post.domain.FeedSortOption;
import com.google.common.base.Preconditions;
import java.util.Arrays;
import org.springframework.stereotype.Component;

@Component
public class GetFeedRequestValidator {

    public void validateCursorPagination(CursorPagination cursorPagination) {
        Preconditions.checkArgument(cursorPagination != null, "유효하지 않은 요청 값입니다.");
        Preconditions.checkArgument(cursorPagination.pageSize() != null, "페이지 수는 null일 수 없습니다.");
        Preconditions.checkArgument(cursorPagination.isPageSizeInRange(), "요청한 페이지 수의 범위가 올바르지 않습니다.");
    }

    public void validateSortOption(FeedSortOption sort) {
        Preconditions.checkArgument(sort != null, "유효하지 않은 요청 값입니다.");
        Arrays.stream(FeedSortOption.values())
            .filter(feedSortOption -> feedSortOption.equals(sort))
            .findAny().orElseThrow(() -> new IllegalArgumentException("올바른 정렬 값이 아닙니다."));
    }
}
