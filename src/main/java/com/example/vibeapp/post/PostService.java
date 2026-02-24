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

    public List<PostListDto> findAll() {
        return postRepository.findAll().stream()
                .map(PostListDto::from)
                .collect(Collectors.toList());
    }

    public PostResponseDTO findById(Long no) {
        postRepository.incrementViews(no);
        Post post = postRepository.findById(no);
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

    public PostResponseDTO update(Long no, PostUpdateDto dto) {
        Post post = postRepository.findById(no);
        if (post != null) {
            post.setTitle(dto.title());
            post.setContent(dto.content());
            post.setUpdatedAt(java.time.LocalDateTime.now());
            postRepository.update(post);
        }
        return PostResponseDTO.from(post);
    }

    public void delete(Long no) {
        postRepository.delete(no);
    }

    public List<PostListDto> findAll(int page, int size) {
        int offset = (page - 1) * size;
        List<Post> pagedPosts = postRepository.findAllWithPaging(offset, size);
        return pagedPosts.stream()
                .map(PostListDto::from)
                .collect(Collectors.toList());
    }

    public int getTotalPages(int size) {
        int totalPosts = postRepository.countAll();
        return (int) Math.ceil((double) totalPosts / size);
    }
}
