package com.javaStudy.apiDocs.Service;

import com.javaStudy.apiDocs.Entity.Member;
import com.javaStudy.apiDocs.Repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member createMember(Member member){
        Member createdMember = memberRepository.save(member);
        return createdMember;
    }
}
