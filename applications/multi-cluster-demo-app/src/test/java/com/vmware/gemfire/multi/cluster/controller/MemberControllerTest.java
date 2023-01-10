package com.vmware.gemfire.multi.cluster.controller;

import com.vmware.gemfire.multi.cluster.controller.controller.MemberController;
import com.vmware.gemfire.multi.cluster.controller.domain.Member;
import com.vmware.gemfire.multi.cluster.controller.repository.MemberRepository;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MemberControllerTest {

       @Mock
       private MemberRepository memberRepository;

       @Mock
       private MemberController subject;
       private Member expectedMember = JavaBeanGeneratorCreator.of(Member.class).create();

       @Test
       void given_claim_when_save_then_when_read_you_have_claim_returned() {

           Mockito.when(memberRepository.findById(anyString())).thenReturn(Optional.of(expectedMember));

           subject = new MemberController(memberRepository);

           subject.saveMember(expectedMember);

           var actual = subject.findMemberId(expectedMember.getId());
           assertEquals(expectedMember, actual);

           verify(this.memberRepository).save(any());
       }
}