package com.example.vibeapp.post;

import com.example.vibeapp.post.dto.PostCreateDto;
import com.example.vibeapp.post.dto.PostListDto;
import com.example.vibeapp.post.dto.PostResponseDTO;
import com.example.vibeapp.post.dto.PostUpdateDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostResponseDTO findById(Long id) {
        Post post = postRepository.findById(id);
        if (post != null) {
            post.setViews(post.getViews() + 1);
        }
        return PostResponseDTO.from(post);
    }

    public PostResponseDTO save(PostCreateDto dto) {
        Post post = dto.toEntity();
        post.setCreatedAt(java.time.LocalDateTime.now());
        post.setUpdatedAt(null);
        post.setViews(0);
        postRepository.save(post);
        return PostResponseDTO.from(post);
    }

    public PostResponseDTO update(Long id, PostUpdateDto dto) {
        Post post = postRepository.findById(id);
        if (post != null) {
            post.setTitle(dto.title());
            post.setContent(dto.content());
            post.setUpdatedAt(java.time.LocalDateTime.now());
        }
        return PostResponseDTO.from(post);
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    public List<PostListDto> findAll(int page, int size) {
        List<Post> allPosts = postRepository.findAll();
        int fromIndex = (page - 1) * size;
        if (fromIndex >= allPosts.size()) {
            return java.util.Collections.emptyList();
        }
        int toIndex = Math.min(fromIndex + size, allPosts.size());
        return allPosts.subList(fromIndex, toIndex).stream()
                .map(PostListDto::from)
                .collect(Collectors.toList());
    }

    public int getTotalPages(int size) {
        int totalPosts = postRepository.findAll().size();
        return (int) Math.ceil((double) totalPosts / size);
    }
}
