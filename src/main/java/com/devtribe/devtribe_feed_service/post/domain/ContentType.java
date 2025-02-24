package com.devtribe.devtribe_feed_service.post.domain;

/**
 * 콘텐츠 유형을 정의하는 열거형.
 * <p>
 * 콘텐츠 유형별 설명:
 * <ul>
 *     <li>{@link #USER} - 일반 사용자가 생성하는 콘텐츠</li>
 *     <li>{@link #TRIBE} - 커뮤니티에서 생성되는 콘텐츠 (커뮤니티를 의미하는 비즈니스 명칭)</li>
 *     <li>{@link #CHANNEL} - RSS 구독 채널 기반 콘텐츠 (예: 기술 블로그)</li>
 * </ul>
 */
public enum ContentType {
    USER, TRIBE, CHANNEL
}
