package com.devtribe.devtribe_feed_service.global.common;

import java.util.List;

public record PageResponse<T>(List<T> data, int pageNo) {

}
