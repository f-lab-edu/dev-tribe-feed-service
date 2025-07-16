package com.devtribe.domain.user.entity;

import com.devtribe.global.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class User extends BaseEntity {

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

    @Builder
    public User(String email, String nickname, String password, String biography,
        String companyName, String jobTitle, String githubUrl, String linkedinUrl, String blogUrl) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.biography = biography;
        this.companyName = companyName;
        this.jobTitle = jobTitle;
        this.githubUrl = githubUrl;
        this.linkedinUrl = linkedinUrl;
        this.blogUrl = blogUrl;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }
}
