package com.vmware.gemfire.multi.cluster.controller;

import com.vmware.gemfire.multi.cluster.controller.controller.ClaimController;
import com.vmware.gemfire.multi.cluster.controller.domain.Claim;
import com.vmware.gemfire.multi.cluster.controller.repository.ClaimRepository;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class ClaimControllerTest {

    @Mock
    private ClaimRepository claimRepository;

    @Mock
    private ClaimController subject;
    private Claim expectedClaim = JavaBeanGeneratorCreator.of(Claim.class).create();

    @Test
    void given_claim_when_save_then_when_read_you_have_claim_returned() {

        Mockito.when(claimRepository.findById(anyString())).thenReturn(Optional.of(expectedClaim));

        subject = new ClaimController(claimRepository);

        subject.saveClaim(expectedClaim);

        var actual = subject.findClaimId(expectedClaim.getId());
        assertEquals(expectedClaim, actual);
    }
}