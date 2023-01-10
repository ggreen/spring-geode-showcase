package com.vmware.gemfire.multi.cluster.controller.controller;

import com.vmware.gemfire.multi.cluster.controller.domain.Member;
import com.vmware.gemfire.multi.cluster.controller.repository.MemberRepository;
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
