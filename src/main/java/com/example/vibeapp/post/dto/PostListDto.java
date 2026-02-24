package com.example.vibeapp.post.dto;

import com.example.vibeapp.post.Post;
import java.time.LocalDateTime;

public record PostListDto(
        Long id,
        String title,
        LocalDateTime createdAt,
        Integer views) {
    public static PostListDto from(Post entity) {
        if (entity == null)
            return null;
        return new PostListDto(
                entity.getId(),
                entity.getTitle(),
                entity.getCreatedAt(),
                entity.getViews());
    }
}
