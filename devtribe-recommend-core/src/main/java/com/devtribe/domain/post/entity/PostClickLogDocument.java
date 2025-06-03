package com.devtribe.domain.post.entity;

import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "post-click-logs")
public class PostClickLogDocument {

    @Id
    private Long id;

    @Field(type = FieldType.Long)
    private Long postId;

    @Field(type = FieldType.Long)
    private Long userId;

    @Field(type = FieldType.Text)
    private String careerInterest;

    @Field(type = FieldType.Text)
    private String careerLevel;

    @Field(type = FieldType.Date, format = {DateFormat.date_hour_minute_second_millis, DateFormat.epoch_millis})
    private Instant timestamp;

    public PostClickLogDocument(
        Long postId,
        Long userId,
        String careerLevel,
        String careerInterest
    ) {
        this.postId = postId;
        this.userId = userId;
        this.careerLevel = careerLevel;
        this.careerInterest = careerInterest;
        this.timestamp = Instant.now();
    }

    @Override
    public String toString() {
        return "PostClickLogDocument{" +
            "id=" + id +
            ", postId=" + postId +
            ", userId=" + userId +
            ", careerInterest='" + careerInterest + '\'' +
            ", careerLevel='" + careerLevel + '\'' +
            ", timestamp=" + timestamp +
            '}';
    }
}
