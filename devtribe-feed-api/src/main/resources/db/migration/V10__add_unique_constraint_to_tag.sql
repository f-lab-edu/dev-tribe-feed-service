ALTER TABLE `devtribe-feed`.tag
ADD CONSTRAINT unique_tag_name
UNIQUE (name);