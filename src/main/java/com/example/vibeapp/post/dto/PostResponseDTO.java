package com.example.vibeapp.post.dto;

import com.example.vibeapp.post.Post;
import java.time.LocalDateTime;

public record PostResponseDTO(
        Long no,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Integer views,
        String tags) {
    public static PostResponseDTO from(Post entity) {
        if (entity == null)
            return null;
        return new PostResponseDTO(
                entity.getNo(),
                entity.getTitle(),
                entity.getContent(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getViews(),
                null); // Tags will be populated separately
    }
}
