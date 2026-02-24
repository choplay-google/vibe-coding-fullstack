package com.example.vibeapp.post;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostRepository {
    private final List<Post> posts = new ArrayList<>();
    private long nextId = 1;

    public PostRepository() {
        for (long i = 1; i <= 10; i++) {
            save(new Post(
                null,
                "Sample Post Title " + i,
                "This is the content of sample post number " + i + ". It contains some vibe and energy.",
                LocalDateTime.now().minusDays(10 - i),
                LocalDateTime.now().minusDays(10 - i),
                (int) (Math.random() * 1000)
            ));
        }
    }

    public Post save(Post post) {
        if (post.getId() == null) {
            post.setId(nextId++);
        }
        posts.add(post);
        return post;
    }

    public List<Post> findAll() {
        List<Post> reversed = new ArrayList<>(posts);
        java.util.Collections.reverse(reversed);
        return reversed;
    }

    public Post findById(Long id) {
        return posts.stream()
                .filter(post -> post.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void deleteById(Long id) {
        posts.removeIf(post -> post.getId().equals(id));
    }
}
