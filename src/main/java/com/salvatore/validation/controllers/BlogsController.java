package com.salvatore.validation.controllers;

import com.salvatore.validation.entities.Blog;
import com.salvatore.validation.payloads.NewBlogPayload;
import com.salvatore.validation.services.BlogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blogs")
public class BlogsController {
    private final BlogsService blogsService;

    @Autowired
    public BlogsController(BlogsService blogsService) {
        this.blogsService = blogsService;
    }

    @GetMapping
    public Page<Blog> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nome") String orderBy
    ){
        return this.blogsService.findAll(page, size, orderBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Blog createBlog(@RequestBody NewBlogPayload payload){
        return this.blogsService.saveBlog(payload);
    }

    @GetMapping("/{blogId}")
    public Blog getBlogById(@PathVariable long blogId){
        return this.blogsService.findById(blogId);
    }

    @PutMapping("/{blogId}")
    public Blog getBlogByIdAndUpdate(@PathVariable long blogId, @RequestBody NewBlogPayload payload){
        return this.blogsService.findByIdAndUpdate(blogId, payload);
    }

    @DeleteMapping("/{blogId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getBlogIdAndDelete(@PathVariable long blogId) {
        this.blogsService.findByIdAndDelete(blogId);
    }

}
