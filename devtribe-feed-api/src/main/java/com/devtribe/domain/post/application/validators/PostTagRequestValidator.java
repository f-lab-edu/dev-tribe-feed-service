package com.devtribe.domain.post.application.validators;

import com.google.common.base.Preconditions;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PostTagRequestValidator {

    public static final Integer MAX_TAG_LIST_SIZE = 10;

    public void validateTagListSize(List<Long> tags) {
        Preconditions.checkNotNull(tags);
        Preconditions.checkArgument(tags.size() <= MAX_TAG_LIST_SIZE,
            "태그는 최대 " + MAX_TAG_LIST_SIZE + "개까지 등록할 수 있습니다");
    }

}
