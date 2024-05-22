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
    private String content;
    private Long memberId;
    private Long postId;

    public static CommentDto from(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setContent(comment.getContent());
        commentDto.setPostId(comment.getPost().getId());
        commentDto.setMemberId(comment.getMember().getId());
        return commentDto;
    }
}
