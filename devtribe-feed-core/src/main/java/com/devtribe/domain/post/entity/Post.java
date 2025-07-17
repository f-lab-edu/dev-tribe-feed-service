package com.devtribe.domain.post.entity;

import com.devtribe.domain.user.entity.User;
import com.devtribe.global.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Entity
@Table(name = "post")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "thumbnail", nullable = false)
    private String thumbnail;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "publication", nullable = false)
    private Publication publication;

    @Column(name = "upvote_count", nullable = false)
    private Integer upvoteCount;

    @Column(name = "downvote_count", nullable = false)
    private Integer downvoteCount;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Builder
    public Post(String title, String content, Long userId,
        String thumbnail) {
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.thumbnail = thumbnail;
        this.publication = Publication.PUBLIC;
        this.upvoteCount = 0;
        this.downvoteCount = 0;
        this.isDeleted = false;
    }

    public void updatePostDetail(String title, String content, String thumbnail, Publication publication) {
        this.title = title;
        this.content = content;
        this.thumbnail = thumbnail;
        this.publication = publication;
    }

    public void deletePost() {
        this.isDeleted = true;
    }

    public boolean isWrittenBy(User findAuthor) {
        if (this.userId == null || findAuthor == null) {
            return false;
        }

        return this.userId.equals(findAuthor.getId());
    }
}

