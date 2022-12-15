package com.vmare.gemfire.multi.cluster.demo.controller;

import com.vmare.gemfire.multi.cluster.demo.domain.Member;
import com.vmare.gemfire.multi.cluster.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("members")
public class MemberController {

    private final MemberRepository memberRepository;

    @PostMapping
    @RequestMapping("member")
    public void saveMember(@RequestBody Member member) {
        memberRepository.save(member);
    }

    @GetMapping
    @RequestMapping("member/{id}")
    public Member findMemberId(@PathVariable String id) {
        return memberRepository.findById(id).orElse(null);
    }
}
