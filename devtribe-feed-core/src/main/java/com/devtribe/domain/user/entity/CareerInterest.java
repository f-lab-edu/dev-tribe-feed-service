package com.devtribe.domain.user.entity;

import lombok.Getter;

@Getter
public enum CareerInterest {

    BACKEND_DEVELOPER("백엔드 개발자"),
    FRONTEND_DEVELOPER("프론트엔드 개발자"),
    FULLSTACK_DEVELOPER("풀스택 개발자"),
    MOBILE_DEVELOPER("모바일 앱 개발자"),
    DEVOPS_ENGINEER("데브옵스 엔지니어"),
    DATA_ENGINEER("데이터 엔지니어"),
    MACHINE_LEARNING_ENGINEER("머신러닝 엔지니어"),
    DATA_SCIENTIST("데이터 사이언티스트"),
    AI_ENGINEER("AI 엔지니어"),
    GAME_DEVELOPER("게임 개발자"),
    SECURITY_ENGINEER("보안 엔지니어"),
    SYSTEMS_ENGINEER("시스템/인프라 엔지니어"),
    QA_ENGINEER("QA/테스트 엔지니어"),
    EMBEDDED_ENGINEER("임베디드 개발자");

    private final String description;

    CareerInterest(String description) {
        this.description = description;
    }

}
