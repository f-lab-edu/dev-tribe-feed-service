# 테이블 명 변경 (post_vote -> post_vote_log)
ALTER TABLE `devtribe-feed`.post_vote
    RENAME TO `devtribe-feed`.post_vote_log;
