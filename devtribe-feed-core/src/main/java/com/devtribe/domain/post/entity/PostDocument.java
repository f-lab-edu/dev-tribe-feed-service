package com.devtribe.domain.post.entity;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

@Getter
@NoArgsConstructor
@Document(indexName = "post-index")
@Setting(settingPath = "elasticsearch/post-index-setting.json")
@Mapping(mappingPath = "elasticsearch/post-index-mapping.json")
public class PostDocument {

    /*
    post의 pk 와 동일하게
     */
    @Id
    private Long id;

    @Field(type = FieldType.Long)
    private Long userId;

    @Field(type = FieldType.Text, analyzer = "nori")
    private String title;

    @Field(type = FieldType.Text, analyzer = "nori")
    private String content;

    @Field(type = FieldType.Keyword)
    private String publication;

    @Field(type = FieldType.Boolean)
    private Boolean isDeleted;

    @Builder
    public PostDocument(
        Long id,
        Long userId,
        String title,
        String content,
        String publication,
        Boolean isDeleted
    ) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.publication = publication;
        this.isDeleted = isDeleted;
    }

    public static PostDocument of(Post post) {
        return PostDocument.builder()
            .id(post.getId())
            .userId(post.getUserId())
            .title(post.getTitle())
            .content(post.getContent())
            .publication(post.getPublication().name())
            .isDeleted(post.getIsDeleted())
            .build();
    }
}
