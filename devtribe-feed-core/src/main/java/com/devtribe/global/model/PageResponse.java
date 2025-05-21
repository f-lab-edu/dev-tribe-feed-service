package com.devtribe.global.model;

import java.util.List;

public record PageResponse<T>(List<T> data, int pageNo) {

}
