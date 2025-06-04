ALTER TABLE `devtribe-feed`.post_tag
ADD CONSTRAINT unique_post_tag_id
UNIQUE (post_id, tag_id);