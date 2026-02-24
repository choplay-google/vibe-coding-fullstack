package com.example.vibeapp.post;

import com.example.vibeapp.post.dto.PostCreateDto;
import com.example.vibeapp.post.dto.PostListDto;
import com.example.vibeapp.post.dto.PostResponseDTO;
import com.example.vibeapp.post.dto.PostUpdateDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
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

    @Transactional
    public PostResponseDTO findById(Long no) {
        Post post = postRepository.findById(no);
        if (post == null) {
            throw new IllegalArgumentException("게시글을 찾을 수 없습니다. (no: " + no + ")");
        }
        
        // JPA 변경 감지(Dirty Checking): 트랜잭션 내에서 엔티티 상태 변경 시 자동 update 쿼리 실행
        post.incrementViews();
        
        String tags = post.getTags().stream()
                .map(PostTag::getTagName)
                .collect(Collectors.joining(", "));
                
        PostResponseDTO dto = PostResponseDTO.from(post);
        return new PostResponseDTO(dto.no(), dto.title(), dto.content(), dto.createdAt(), dto.updatedAt(), dto.views(), tags);
    }

    @Transactional
    public PostResponseDTO save(PostCreateDto dto) {
        Post post = dto.toEntity();
        post.setCreatedAt(LocalDateTime.now());
        post.setViews(0);
        
        // Tags 처리 - 연관관계 편의 메서드 활용
        processTags(post, dto.tags());
        
        // Post만 persist하면 CascadeType.ALL에 의해 PostTag도 자동 저장됨 (영속성 전이)
        postRepository.save(post);
        
        return findById(post.getNo());
    }

    @Transactional
    public PostResponseDTO update(Long no, PostUpdateDto dto) {
        Post post = postRepository.findById(no);
        if (post != null) {
            // Dirty Checking: 트랜잭션 내에서 엔티티 수정 시 영속성 컨텍스트가 추적하다가 커밋 시점에 flush
            post.update(dto.title(), dto.content());
            
            // 기존 태그 삭제 및 신규 태그 추가 (orphanRemoval = true에 의해 리스트에서 제거된 엔티티는 자동 delete)
            post.getTags().clear();
            processTags(post, dto.tags());
        }
        return findById(no);
    }

    @Transactional
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

    private void processTags(Post post, String tagsString) {
        if (tagsString != null && !tagsString.trim().isEmpty()) {
            String[] tags = tagsString.split(",");
            for (String tag : tags) {
                String trimmedTag = tag.trim();
                if (!trimmedTag.isEmpty()) {
                    post.addTag(new PostTag(trimmedTag));
                }
            }
        }
    }
}
