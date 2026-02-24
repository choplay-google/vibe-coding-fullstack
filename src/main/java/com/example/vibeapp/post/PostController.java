package com.example.vibeapp.post;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public String listPosts(@org.springframework.web.bind.annotation.RequestParam(name = "page", defaultValue = "1") int page, Model model) {
        int size = 5;
        model.addAttribute("posts", postService.getPostsByPage(page, size));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postService.getTotalPages(size));
        return "posts";
    }

    @GetMapping("/posts/{no}")
    public String viewPost(@PathVariable("no") Long no, Model model) {
        Post post = postService.getPostByNo(no);
        model.addAttribute("post", post);
        return "post_detail";
    }

    @GetMapping("/posts/new")
    public String newPostForm() {
        return "post_new_form";
    }

    @PostMapping("/posts/add")
    public String addPost(@ModelAttribute Post post) {
        postService.createPost(post);
        return "redirect:/posts";
    }

    @GetMapping("/posts/{no}/edit")
    public String editPostForm(@PathVariable("no") Long no, Model model) {
        Post post = postService.getPostByNo(no);
        model.addAttribute("post", post);
        return "post_edit_form";
    }

    @PostMapping("/posts/{no}/save")
    public String savePost(@PathVariable("no") Long no, @ModelAttribute Post post) {
        postService.updatePost(no, post.getTitle(), post.getContent());
        return "redirect:/posts/" + no;
    }

    @PostMapping("/posts/{no}/delete")
    public String deletePost(@PathVariable("no") Long no) {
        postService.deletePost(no);
        return "redirect:/posts";
    }
}
