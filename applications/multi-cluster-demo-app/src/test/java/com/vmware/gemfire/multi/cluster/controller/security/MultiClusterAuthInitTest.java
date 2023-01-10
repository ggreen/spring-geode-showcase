package com.vmware.gemfire.multi.cluster.controller.security;

import com.vmware.gemfire.multi.cluster.controller.security.MultiClusterAuthInit;
import org.apache.geode.distributed.DistributedMember;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class MultiClusterAuthInitTest {

    private Properties securityProps;

    @Mock
    private DistributedMember distributedMember;
    private boolean isPeer = false;
    
    @Mock
    private Environment environment;

    private String expectedCluster2UserName = "junit2";
    private String expectedCluster2Password = "HI2!!!";
    private String cluster2MemberIdRegExp = "^.*[2]\\]$";
    private Properties expectedCredentialsCluster2;
    private Properties expectedCredentialsCluster1;

    private MultiClusterAuthInit subject;
    private String expectedCluster1UserName = "junit1";
    private String expectedCluster1Password = "HI1!!!";

    @BeforeEach
    void setUp() {
        securityProps = new Properties();

        expectedCredentialsCluster2 = new Properties();
        expectedCredentialsCluster2.setProperty("security-username", expectedCluster2UserName);
        expectedCredentialsCluster2.setProperty("security-password", expectedCluster2Password);


        expectedCredentialsCluster1 = new Properties();
        expectedCredentialsCluster1.setProperty("security-username", expectedCluster1UserName);
        expectedCredentialsCluster1.setProperty("security-password", expectedCluster1Password);



        subject = new MultiClusterAuthInit();
        subject.setEnvironment(environment);
    }

    @Test
    void instance() {

        assertNotNull(MultiClusterAuthInit.instance());
    }

    @Test
    void given_memberInCluster2_when_getProperties_then_Return_Cluster2_credentials() {

        String cluster2MemberId = "localhost<ec>[10002]";

        when(environment.getProperty(anyString()))
                .thenReturn(cluster2MemberIdRegExp)
                .thenReturn(expectedCluster2UserName)
                .thenReturn(expectedCluster2Password);

        when(distributedMember.getId()).thenReturn(cluster2MemberId);

        var actual = subject.getCredentials(securityProps, distributedMember,isPeer);

        assertEquals(expectedCredentialsCluster2, actual);
    }

    @Test
    void given_secondClusterExpression_isNull_when_isSecondary_then_False() {

        assertFalse(subject.isSecondaryCluster(distributedMember));
    }

    @Test
    void given_secondClusterExpression_isEmpty_when_isSecondary_then_False() {

//        when(environment.getProperty("spring.data.gemfire.cluster2.regExp")).thenReturn("");

        assertFalse(subject.isSecondaryCluster(distributedMember));
    }

    @Test
    void given_cluster1Member_when_getProperties_then_return_Cluster1_Credentials() {

        String cluster1MemberId = "localhost<ec>[10001]";

        when(environment.getProperty(anyString()))
                .thenReturn(cluster2MemberIdRegExp)
                .thenReturn(expectedCluster1UserName)
                .thenReturn(expectedCluster1Password);

        when(distributedMember.getId()).thenReturn(cluster1MemberId);

        var actual = subject.getCredentials(securityProps, distributedMember,isPeer);

        assertEquals(expectedCredentialsCluster1, actual);
    }

}