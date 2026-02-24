package com.example.vibeapp.post;

import jakarta.persistence.*;

@Entity
@Table(name = "POST_TAGS")
public class PostTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_NO")
    private Post post;

    @Column(name = "TAG_NAME", nullable = false)
    private String tagName;

    public PostTag() {
    }

    public PostTag(String tagName) {
        this.tagName = tagName;
    }

    public PostTag(Post post, String tagName) {
        this.post = post;
        this.tagName = tagName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
