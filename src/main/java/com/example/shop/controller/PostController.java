package com.example.shop.controller;

import com.example.shop.domain.Post;
import com.example.shop.dto.MemberDto;
import com.example.shop.dto.PostDto;
import com.example.shop.service.MemberService;
import com.example.shop.service.PostService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final MemberService memberService;

    @Autowired
    public PostController(PostService postService, MemberService memberService) {
        this.postService = postService;
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<PostDto> registerPost(@RequestBody PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setMember(memberService.getMemberById(postDto.getMemberId()).orElse(null));

        Post registerdPost = postService.registerPost(post);

        return ResponseEntity.ok(PostDto.from(registerdPost));
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        List<PostDto> postDtos = new ArrayList<>();
        for (Post post: posts) {
            PostDto postDto = PostDto.from(post);
            postDtos.add(postDto);
        }
        return ResponseEntity.ok(postDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") Long id) {
        Optional<Post> postOptional = postService.getPostById(id);
        if (postOptional.isPresent()) {
            PostDto postDto = PostDto.from(postOptional.get());
            return ResponseEntity.ok(postDto);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long id, @RequestBody PostDto postDto) {
        try {
            Post updatedPost = postService.updatePost(id, postDto);
            return ResponseEntity.ok(PostDto.from(updatedPost));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePostById(@PathVariable(name = "id") Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

}
