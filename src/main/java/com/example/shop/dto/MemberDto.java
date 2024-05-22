package com.example.shop.dto;

import com.example.shop.domain.Member;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MemberDto {
    private String name;
    private String email;

    public static MemberDto from(Member member) {
        MemberDto memberDto = new MemberDto();
        memberDto.setName(member.getName());
        memberDto.setEmail(member.getEmail());
        return memberDto;
    }

    public static Member to(MemberDto memberDto) {
        Member member = new Member();
        member.setName(memberDto.getName());
        member.setEmail(memberDto.getEmail());
        return member;
    }

}
