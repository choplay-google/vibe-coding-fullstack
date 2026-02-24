package com.example.vibeapp.post;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post findById(Long id) {
        Post post = postRepository.findById(id);
        if (post != null) {
            post.setViews(post.getViews() + 1);
        }
        return post;
    }

    public void save(Post post) {
        post.setCreatedAt(java.time.LocalDateTime.now());
        post.setUpdatedAt(null);
        post.setViews(0);
        postRepository.save(post);
    }

    public void update(Long id, String title, String content) {
        Post post = postRepository.findById(id);
        if (post != null) {
            post.setTitle(title);
            post.setContent(content);
            post.setUpdatedAt(java.time.LocalDateTime.now());
        }
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    public List<Post> findAll(int page, int size) {
        List<Post> allPosts = postRepository.findAll();
        int fromIndex = (page - 1) * size;
        if (fromIndex >= allPosts.size()) {
            return java.util.Collections.emptyList();
        }
        int toIndex = Math.min(fromIndex + size, allPosts.size());
        return allPosts.subList(fromIndex, toIndex);
    }

    public int getTotalPages(int size) {
        int totalPosts = postRepository.findAll().size();
        return (int) Math.ceil((double) totalPosts / size);
    }
}
