package com.devtribe.domain.post.application.validators;

import com.devtribe.domain.post.application.dtos.PostQueryRequest;
import com.devtribe.domain.post.application.dtos.PostSearchRequest;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Component;

@Component
public class PostRequestValidator {

    public static final Integer MAX_TITLE_LENGTH = 250;
    public static final Integer MAX_BODY_LENGTH = 1000;
    public static final int MAX_KEYWORD_LENGTH = 20;
    public static final int MAX_PAGE_SIZE = 100;

    public void validateTitle(String title) {
        Preconditions.checkArgument(title != null, "제목은 필수값입니다.");
        Preconditions.checkArgument(!title.isEmpty(), "제목은 비어있을 수 없습니다.");
        Preconditions.checkArgument(title.length() <= MAX_TITLE_LENGTH,
            "제목은 " + MAX_TITLE_LENGTH + "자를 초과할 수 없습니다.");
    }

    public void validateBody(String content) {
        if (content == null) {
            return;
        }

        Preconditions.checkArgument(content.length() <= MAX_BODY_LENGTH,
            "본문은 " + MAX_BODY_LENGTH + "자를 초과할 수 없습니다.");
    }

    public void validateSearchRequest(PostSearchRequest request) {
        validateKeyword(request.keyword());
        validatePageRequest(request.size());
    }

    public void validateQueryRequest(PostQueryRequest request) {
        validateAuthor(request.authorId());
        validatePageRequest(request.size());

        if (request.startDate() == null && request.endDate() == null) {
            return;
        }
        Preconditions.checkArgument(
            request.startDate() != null && request.endDate() != null,
            "날짜 범위가 유효하지 않습니다."
        );

        Preconditions.checkArgument(
            !request.startDate().isAfter(request.endDate()),
            "시작 날짜는 종료 날짜보다 이전이어야 합니다."
        );
    }

    private void validateKeyword(String keyword) {
        Preconditions.checkArgument(
            keyword != null && !keyword.isEmpty(),
            "키워드는 필수값입니다.");
        Preconditions.checkArgument(
            keyword.length() <= MAX_KEYWORD_LENGTH,
            "최대 키워드 길이 " + MAX_KEYWORD_LENGTH + "자를 넘을 수 없습니다.");
    }

    private void validateAuthor(Long authorId) {
        Preconditions.checkArgument(
            authorId == null || authorId > 0,
            "작성자가 유효하지 않습니다.");
    }

    private void validatePageRequest(int size) {
        Preconditions.checkArgument(
            size <= MAX_PAGE_SIZE,
            "요청한 페이지 수의 범위가 올바르지 않습니다. 최대 요청 가능한 페이지 수는 " + MAX_PAGE_SIZE + " 입니다.");
    }

}
