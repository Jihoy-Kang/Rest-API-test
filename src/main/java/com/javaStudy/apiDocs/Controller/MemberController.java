package com.javaStudy.apiDocs.Controller;

import com.javaStudy.apiDocs.Dto.MemberDto;
import com.javaStudy.apiDocs.Entity.Member;
import com.javaStudy.apiDocs.Repository.MemberRepository;
import com.javaStudy.apiDocs.Service.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/members")
public class MemberController {

    MemberService memberService;
    MemberRepository memberRepository;

    public MemberController(MemberService memberService, MemberRepository memberRepository) {
        this.memberService = memberService;
        this.memberRepository = memberRepository;
    }

   /* @PostMapping
    public ResponseEntity postMemberV1(@RequestParam ("name") String name,
                                     @RequestParam ("email") String email){
       Map<String,String> map = new HashMap<>();
       map.put("name", name);
       map.put("email", email);

        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }*/

    @PostMapping
    public ResponseEntity postMemberV2(@RequestBody MemberDto memberDto){

        return new ResponseEntity<>(memberDto, HttpStatus.CREATED);
    }

    @PatchMapping("/{member-id}")
    public ResponseEntity patchMember(@PathVariable("member-id") long memberId,
                                      @RequestBody Member member){
        Member findMember = memberRepository.findById(memberId).orElseThrow();
        Optional.ofNullable(member.getName())
                .ifPresent(name -> findMember.setName(name));
        Optional.ofNullable(member.getEmail())
                .ifPresent(email -> findMember.setEmail(email));

        Member response = memberRepository.save(findMember);


        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{member-id}")
    public ResponseEntity getMember(@PathVariable("member-id") long memberId){
        Member response = memberRepository.findById(memberId).orElseThrow();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity getMembers(int page, int size){
        Page<Member> memberPage = memberRepository.findAll(PageRequest.of(page,size, Sort.by("memberId").descending()));
        List<Member> members = memberPage.getContent();
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember(@PathVariable("member-id") long memberId){
        Member findMember = memberRepository.findById(memberId).orElseThrow();
        memberRepository.delete(findMember);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
