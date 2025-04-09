package com.devtribe.devtribe_feed_service.post.domain;

import com.devtribe.devtribe_feed_service.post.application.dtos.UpdatePostRequest;
import com.devtribe.devtribe_feed_service.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@Getter
@Entity
@Table(name = "post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

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

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private Long createdBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    private Long updatedBy;

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

    public void updatePostDetail(UpdatePostRequest request) {
        this.title = request.title();
        this.content = request.content();
        this.thumbnail = request.thumbnail();
        this.publication = request.publication();
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

