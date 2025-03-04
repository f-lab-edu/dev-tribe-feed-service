CREATE TABLE user
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    email        VARCHAR(320) NOT NULL,
    nickname     VARCHAR(255) NOT NULL,
    password     VARCHAR(64)  NOT NULL,
    biography    VARCHAR(100),
    company_name VARCHAR(255),
    job_title    VARCHAR(255),
    github_url   VARCHAR(1024),
    linkedin_url VARCHAR(1024),
    blog_url     VARCHAR(1024),
    created_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE user_follow
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    follower_id BIGINT    NOT NULL,
    followee_id BIGINT    NOT NULL,
    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY unique_follow (follower_id, followee_id)
);

CREATE TABLE post
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id        BIGINT       NOT NULL,
    title          VARCHAR(255) NOT NULL,
    content        VARCHAR(1000),
    thumbnail      VARCHAR(1000),
    publication    varchar(255) NOT NULL,
    upvote_count   INT UNSIGNED NOT NULL DEFAULT 0,
    downvote_count INT UNSIGNED NOT NULL DEFAULT 0,
    created_at     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE comment
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    post_id        BIGINT       NOT NULL,
    user_id        BIGINT       NOT NULL,
    content        VARCHAR(250),
    upvote_count   INT UNSIGNED NOT NULL DEFAULT 0,
    downvote_count INT UNSIGNED NOT NULL DEFAULT 0,
    created_at     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE vote
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id     BIGINT       NOT NULL,
    target_id   BIGINT       NOT NULL,
    target_type VARCHAR(255) NOT NULL,
    vote_type   VARCHAR(255) NOT NULL,
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE bookmark
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id    BIGINT    NOT NULL,
    post_id    BIGINT    NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY unique_bookmark (user_id, post_id)
);

CREATE TABLE tag
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE post_tag
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    post_id    BIGINT    NOT NULL,
    tag_id     BIGINT    NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY unique_post_tag (post_id, tag_id)
)