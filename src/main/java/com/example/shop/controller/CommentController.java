package com.example.shop.controller;

import com.example.shop.domain.Comment;
import com.example.shop.dto.CommentDto;
import com.example.shop.service.CommentService;
import com.example.shop.service.MemberService;
import com.example.shop.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;
    private final MemberService memberService;

    @Autowired
    public CommentController(CommentService commentService, PostService postService, MemberService memberService) {
        this.commentService = commentService;
        this.postService = postService;
        this.memberService = memberService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<CommentDto> registerComment(@RequestBody CommentDto commentDto) {
        Comment comment = new Comment();

        comment.setContent(commentDto.getContent());
        comment.setPost(postService.getPostById(commentDto.getPostId()).orElse(null));
        comment.setMember(memberService.getMemberById(commentDto.getMemberId()).orElse(null));

        Comment registerdComment = commentService.registerComment(comment);
        return ResponseEntity.ok(CommentDto.from(registerdComment));
    }

    // READ
    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(name = "id") Long id) {
        Optional<Comment> commentOptional = commentService.getCommentById(id);

        if (commentOptional.isPresent()) {
            CommentDto commentDto = CommentDto.from(commentOptional.get());
            return ResponseEntity.ok(commentDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable(name = "id") Long id) {
        List<Comment> comments = commentService.getCommentsByPostId(id);
        List<CommentDto> commentDtos = new ArrayList<>();

        for (Comment comment: comments) {
            CommentDto commentDto = CommentDto.from(comment);
            commentDtos.add(commentDto);
        }

        return ResponseEntity.ok(commentDtos);
    }


    // UPDATE
    @PatchMapping("/{id}")
    public ResponseEntity<CommentDto> updateCommentById(@PathVariable(name = "id") Long id, @RequestBody CommentDto commentDto) {
        try {
            Comment updatedComment = commentService.updateComment(id, commentDto);
            return ResponseEntity.ok(CommentDto.from(updatedComment));
        } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
