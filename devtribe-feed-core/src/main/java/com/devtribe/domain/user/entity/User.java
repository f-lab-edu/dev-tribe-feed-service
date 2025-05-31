package com.devtribe.domain.user.entity;

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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "biography")
    private String biography;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "github_url")
    private String githubUrl;

    @Column(name = "linkedin_url")
    private String linkedinUrl;

    @Column(name = "blog_url")
    private String blogUrl;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "career_level")
    private CareerLevel careerLevel;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "career_interest")
    private CareerInterest careerInterest;

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
    public User(
        String email,
        String nickname,
        String password,
        String biography,
        String companyName,
        String jobTitle,
        String githubUrl,
        String linkedinUrl,
        String blogUrl,
        CareerLevel careerLevel,
        CareerInterest careerInterest
    ) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.biography = biography;
        this.companyName = companyName;
        this.jobTitle = jobTitle;
        this.githubUrl = githubUrl;
        this.linkedinUrl = linkedinUrl;
        this.blogUrl = blogUrl;
        this.careerLevel = careerLevel;
        this.careerInterest = careerInterest;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public void updateProfile(

    ) {

    }
}
