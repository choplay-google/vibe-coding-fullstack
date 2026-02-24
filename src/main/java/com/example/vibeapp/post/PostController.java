package com.example.vibeapp.post;

import com.example.vibeapp.post.dto.PostCreateDto;
import com.example.vibeapp.post.dto.PostListDto;
import com.example.vibeapp.post.dto.PostResponseDTO;
import com.example.vibeapp.post.dto.PostUpdateDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<PostListDto>> list(@RequestParam(name = "page", defaultValue = "1") int page) {
        int size = 5;
        List<PostListDto> posts = postService.findAll(page, size);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{no}")
    public ResponseEntity<PostResponseDTO> detail(@PathVariable("no") Long no) {
        PostResponseDTO post = postService.findById(no);
        return ResponseEntity.ok(post);
    }

    @PostMapping
    public ResponseEntity<PostResponseDTO> save(@Valid @RequestBody PostCreateDto postDto) {
        PostResponseDTO savedPost = postService.save(postDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPost);
    }

    @PatchMapping("/{no}")
    public ResponseEntity<PostResponseDTO> update(@PathVariable("no") Long no, @Valid @RequestBody PostUpdateDto postDto) {
        PostResponseDTO updatedPost = postService.update(no, postDto);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{no}")
    public ResponseEntity<Void> delete(@PathVariable("no") Long no) {
        postService.delete(no);
        return ResponseEntity.noContent().build();
    }
}
