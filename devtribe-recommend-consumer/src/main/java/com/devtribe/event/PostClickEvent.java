package com.devtribe.event;

public record PostClickEvent(
    Long postId,
    Long userId,
    String careerInterest,
    String careerLevel
) {

}
