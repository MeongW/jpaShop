package com.example.shop.dto;

import com.example.shop.domain.Comment;
import com.example.shop.domain.Member;
import com.example.shop.domain.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDto {
    private Long id;
    private String content;
    private LocalDateTime commentDate;
    private MemberDto member;
    private PostDto post;

    public static CommentDto from(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setContent(comment.getContent());
        commentDto.setCommentDate(comment.getCommentDate());
        if (comment.getMember() != null) {
            commentDto.setMember(MemberDto.from(comment.getMember()));
        }
        if (comment.getPost() != null) {
            commentDto.setPost(PostDto.from(comment.getPost()));
        }
        return commentDto;
    }
}
