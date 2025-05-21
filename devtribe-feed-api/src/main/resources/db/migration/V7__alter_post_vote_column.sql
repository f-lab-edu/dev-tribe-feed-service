# target_id 컬럼명 변경
ALTER TABLE `devtribe-feed`.post_vote
    CHANGE target_id post_id BIGINT NOT NULL;

# target_type 컬럼 제거
ALTER TABLE `devtribe-feed`.post_vote
DROP COLUMN target_type;