package com.vmare.gemfire.multi.cluster.demo.controller;

import com.vmare.gemfire.multi.cluster.demo.domain.Claim;
import com.vmare.gemfire.multi.cluster.demo.repository.ClaimRepository;
import com.vmare.gemfire.multi.cluster.demo.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("claims")
@AllArgsConstructor
public class ClaimController {
    private final ClaimRepository claimRepository;

    @PostMapping("claim")
    public void saveClaim(@RequestBody Claim claim) {
        claimRepository.save(claim);
    }

    public Claim findClaimId(String id) {
        return claimRepository.findById(id).orElse(null);
    }
}
