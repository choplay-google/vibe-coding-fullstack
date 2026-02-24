package com.example.vibeapp.post;

import com.example.vibeapp.post.dto.PostCreateDto;
import com.example.vibeapp.post.dto.PostResponseDTO;
import com.example.vibeapp.post.dto.PostUpdateDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public String list(@RequestParam(name = "page", defaultValue = "1") int page, Model model) {
        int size = 5;
        model.addAttribute("posts", postService.findAll(page, size));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postService.getTotalPages(size));
        return "post/posts";
    }

    @GetMapping("/posts/{no}")
    public String detail(@PathVariable("no") Long no, Model model) {
        PostResponseDTO post = postService.findById(no);
        model.addAttribute("post", post);
        return "post/post_detail";
    }

    @GetMapping("/posts/new")
    public String createForm(Model model) {
        model.addAttribute("post", new PostCreateDto());
        return "post/post_new_form";
    }

    @PostMapping("/posts/add")
    public String save(@Valid @ModelAttribute("post") PostCreateDto postDto, BindingResult result) {
        if (result.hasErrors()) {
            return "post/post_new_form";
        }
        postService.save(postDto);
        return "redirect:/posts";
    }

    @GetMapping("/posts/{no}/edit")
    public String editForm(@PathVariable("no") Long no, Model model) {
        PostResponseDTO post = postService.findById(no);
        model.addAttribute("post", post);
        return "post/post_edit_form";
    }

    @PostMapping("/posts/{no}/save")
    public String update(@PathVariable("no") Long no, @Valid @ModelAttribute("post") PostUpdateDto postDto,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("post", postService.findById(no));
            return "post/post_edit_form";
        }
        postService.update(no, postDto);
        return "redirect:/posts/" + no;
    }

    @PostMapping("/posts/{no}/delete")
    public String delete(@PathVariable("no") Long no) {
        postService.delete(no);
        return "redirect:/posts";
    }
}
