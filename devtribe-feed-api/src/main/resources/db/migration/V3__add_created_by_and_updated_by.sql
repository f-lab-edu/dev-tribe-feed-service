ALTER TABLE user
    ADD COLUMN created_by BIGINT,
    ADD COLUMN updated_by BIGINT;

ALTER TABLE user_follow
    ADD COLUMN created_by BIGINT NOT NULL,
    ADD COLUMN updated_by BIGINT;

ALTER TABLE post
    ADD COLUMN created_by BIGINT NOT NULL,
    ADD COLUMN updated_by BIGINT;

ALTER TABLE comment
    ADD COLUMN created_by BIGINT NOT NULL,
    ADD COLUMN updated_by BIGINT;

ALTER TABLE vote
    ADD COLUMN created_by BIGINT NOT NULL,
    ADD COLUMN updated_by BIGINT;

ALTER TABLE bookmark
    ADD COLUMN created_by BIGINT NOT NULL;

ALTER TABLE tag
    ADD COLUMN created_by BIGINT NOT NULL,
    ADD COLUMN updated_by BIGINT;

ALTER TABLE post_tag
    ADD COLUMN created_by BIGINT NOT NULL;