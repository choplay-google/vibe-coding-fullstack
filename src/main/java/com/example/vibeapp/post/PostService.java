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
    private final PostTagRepository postTagRepository;

    public PostService(PostRepository postRepository, PostTagRepository postTagRepository) {
        this.postRepository = postRepository;
        this.postTagRepository = postTagRepository;
    }

    public List<PostListDto> findAll() {
        return postRepository.findAll().stream()
                .map(PostListDto::from)
                .collect(Collectors.toList());
    }

    public PostResponseDTO findById(Long no) {
        postRepository.incrementViews(no);
        Post post = postRepository.findById(no);
        String tags = getTagsAsString(no);
        PostResponseDTO dto = PostResponseDTO.from(post);
        return new PostResponseDTO(dto.no(), dto.title(), dto.content(), dto.createdAt(), dto.updatedAt(), dto.views(), tags);
    }

    public PostResponseDTO save(PostCreateDto dto) {
        Post post = dto.toEntity();
        post.setCreatedAt(java.time.LocalDateTime.now());
        post.setUpdatedAt(null);
        post.setViews(0);
        postRepository.save(post);
        
        saveTags(post.getNo(), dto.tags());
        
        return findById(post.getNo());
    }

    public PostResponseDTO update(Long no, PostUpdateDto dto) {
        Post post = postRepository.findById(no);
        if (post != null) {
            post.setTitle(dto.title());
            post.setContent(dto.content());
            post.setUpdatedAt(java.time.LocalDateTime.now());
            postRepository.update(post);
            
            postTagRepository.deleteByPostNo(no);
            saveTags(no, dto.tags());
        }
        return findById(no);
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

    private void saveTags(Long postNo, String tagsString) {
        if (tagsString != null && !tagsString.trim().isEmpty()) {
            String[] tags = tagsString.split(",");
            for (String tag : tags) {
                String trimmedTag = tag.trim();
                if (!trimmedTag.isEmpty()) {
                    postTagRepository.save(new PostTag(null, postNo, trimmedTag));
                }
            }
        }
    }

    private String getTagsAsString(Long postNo) {
        List<PostTag> postTags = postTagRepository.findByPostNo(postNo);
        if (postTags == null || postTags.isEmpty()) {
            return "";
        }
        return postTags.stream()
                .map(PostTag::getTagName)
                .collect(Collectors.joining(", "));
    }
}
