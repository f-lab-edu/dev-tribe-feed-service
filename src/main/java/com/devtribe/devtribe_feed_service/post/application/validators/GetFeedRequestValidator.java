package com.devtribe.devtribe_feed_service.post.application.validators;

import com.devtribe.devtribe_feed_service.global.common.CursorPagination;
import com.devtribe.devtribe_feed_service.post.domain.FeedSortOption;
import com.google.common.base.Preconditions;
import java.util.Arrays;
import org.springframework.stereotype.Component;

@Component
public class GetFeedRequestValidator {

    public void validateCursorPagination(CursorPagination cursorPagination) {
        if (cursorPagination == null) {
            return;
        }

        Preconditions.checkArgument(!cursorPagination.isPageSizeInRange(), "유효하지 않은 요청 값입니다.");
    }

    public void validateSortOption(String sort) {
        Arrays.stream(FeedSortOption.values())
            .filter(feedSortOption -> feedSortOption.valueEquals(sort))
            .findAny().orElseThrow(() -> new IllegalArgumentException("올바른 정렬 값이 아닙니다."));
    }
}
