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
    public String list(@org.springframework.web.bind.annotation.RequestParam(name = "page", defaultValue = "1") int page, Model model) {
        int size = 5;
        model.addAttribute("posts", postService.findAll(page, size));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postService.getTotalPages(size));
        return "post/posts";
    }

    @GetMapping("/posts/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        Post post = postService.findById(id);
        model.addAttribute("post", post);
        return "post/post_detail";
    }

    @GetMapping("/posts/new")
    public String createForm() {
        return "post/post_new_form";
    }

    @PostMapping("/posts/add")
    public String save(@ModelAttribute Post post) {
        postService.save(post);
        return "redirect:/posts";
    }

    @GetMapping("/posts/{id}/edit")
    public String editForm(@PathVariable("id") Long id, Model model) {
        Post post = postService.findById(id);
        model.addAttribute("post", post);
        return "post/post_edit_form";
    }

    @PostMapping("/posts/{id}/save")
    public String update(@PathVariable("id") Long id, @ModelAttribute Post post) {
        postService.update(id, post.getTitle(), post.getContent());
        return "redirect:/posts/" + id;
    }

    @PostMapping("/posts/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        postService.delete(id);
        return "redirect:/posts";
    }
}
