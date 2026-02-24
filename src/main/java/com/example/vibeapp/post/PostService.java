package com.example.vibeapp.post;

import com.example.vibeapp.post.dto.PostCreateDto;
import com.example.vibeapp.post.dto.PostListDto;
import com.example.vibeapp.post.dto.PostResponseDTO;
import com.example.vibeapp.post.dto.PostUpdateDto;
import com.example.vibeapp.post.mapper.PostMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostMapper postMapper;

    public PostService(PostMapper postMapper) {
        this.postMapper = postMapper;
    }

    public List<PostListDto> findAll() {
        return postMapper.findAll().stream()
                .map(PostListDto::from)
                .collect(Collectors.toList());
    }

    public PostResponseDTO findById(Long no) {
        postMapper.incrementViews(no);
        Post post = postMapper.findById(no);
        return PostResponseDTO.from(post);
    }

    public PostResponseDTO save(PostCreateDto dto) {
        Post post = dto.toEntity();
        post.setCreatedAt(java.time.LocalDateTime.now());
        post.setUpdatedAt(null);
        post.setViews(0);
        postMapper.save(post);
        return PostResponseDTO.from(post);
    }

    public PostResponseDTO update(Long no, PostUpdateDto dto) {
        Post post = postMapper.findById(no);
        if (post != null) {
            post.setTitle(dto.title());
            post.setContent(dto.content());
            post.setUpdatedAt(java.time.LocalDateTime.now());
            postMapper.update(post);
        }
        return PostResponseDTO.from(post);
    }

    public void delete(Long no) {
        postMapper.delete(no);
    }

    public List<PostListDto> findAll(int page, int size) {
        List<Post> allPosts = postMapper.findAll();
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
        int totalPosts = postMapper.findAll().size();
        return (int) Math.ceil((double) totalPosts / size);
    }
}
