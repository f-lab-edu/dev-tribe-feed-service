package com.devtribe.domain.post.entity;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "posts")
public class PostDocument {

    @Id
    @Field(type = FieldType.Long)
    private Long postId;

    @Field(type = FieldType.Keyword)
    private List<String> tags;

    @Field(type = FieldType.Date, format = {DateFormat.date_hour_minute_second_millis, DateFormat.epoch_millis})
    private LocalDateTime createdAt;

    @Field(type = FieldType.Date, format = {DateFormat.date_hour_minute_second_millis, DateFormat.epoch_millis})
    private LocalDateTime updatedAt;

    public PostDocument(
        Long postId,
        List<String> tags,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
    ) {
        this.postId = postId;
        this.tags = tags;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void updateTags(List<String> tags, LocalDateTime updatedAt) {
        this.tags = tags;
        this.updatedAt = updatedAt;
    }
}
