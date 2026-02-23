package com.example.vibeapp.repository;

import com.example.vibeapp.entity.Post;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostRepository {
    private final List<Post> posts = new ArrayList<>();

    public PostRepository() {
        for (long i = 1; i <= 10; i++) {
            posts.add(new Post(
                i,
                "Sample Post Title " + i,
                "This is the content of sample post number " + i + ". It contains some vibe and energy.",
                LocalDateTime.now().minusDays(10 - i),
                LocalDateTime.now().minusDays(10 - i),
                (int) (Math.random() * 1000)
            ));
        }
    }

    public List<Post> findAll() {
        return new ArrayList<>(posts);
    }
}
