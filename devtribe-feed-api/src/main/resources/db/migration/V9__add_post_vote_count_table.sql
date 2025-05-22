CREATE TABLE post_vote_count
(
    post_id    BIGINT       NOT NULL,
    vote_type  VARCHAR(255) NOT NULL,
    vote_count INT UNSIGNED NOT NULL DEFAULT 0,
    created_at TIMESTAMP             DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP             DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (post_id, vote_type)
);