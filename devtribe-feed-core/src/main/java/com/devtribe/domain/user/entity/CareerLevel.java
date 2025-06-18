package com.devtribe.domain.user.entity;

import lombok.Getter;

@Getter
public enum CareerLevel {

    ROOKIE("1년차 미만"),
    ENTRY_LEVEL("1년차"),
    MID_LEVEL("2-3년차"),
    EXPERT_LEVEL("4-5년차"),
    HIGHLY_EXPERT_LEVEL("6-10년차"),
    LEGEND("10년차 이상");

    private final String description;

    CareerLevel(String description) {
        this.description = description;
    }
}
