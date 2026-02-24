package com.example.vibeapp.post.dto;

import com.example.vibeapp.post.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostUpdateDto(
        @NotBlank(message = "제목은 필수입니다.") @Size(max = 100, message = "제목은 100자 이내여야 합니다.") String title,

        @NotBlank(message = "내용은 필수입니다.") String content,
        
        String tags) {

    public PostUpdateDto() {
        this(null, null, null);
    }

    public Post toEntity() {
        Post post = new Post();
        post.setTitle(this.title);
        post.setContent(this.content);
        return post;
    }
}
