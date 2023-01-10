package com.vmware.gemfire.multi.cluster.controller.security;

import org.apache.geode.distributed.DistributedMember;
import org.apache.geode.security.AuthInitialize;
import org.apache.geode.security.AuthenticationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class MultiClusterAuthInit implements AuthInitialize {

    private static final String SECURITY_USERNAME_PROP_NM = "security-username";

    private static final String SECURITY_PASSWORD_PROP_NM = "security-password";
    private static final String SPRING_CLUSTER_2_REGEXP_PROP_NM = "spring.data.gemfire.security.cluster2.regExp";
    private static final String SPRING_USERNAME_PROP_NM = "spring.data.gemfire.security.username";
    private static final String SPRING_PASSWORD_PROP_NM = "spring.data.gemfire.security.password";
    private static final String SPRING_USERNAME2_PROP_NM = "spring.data.gemfire.security.cluster2.username";;
    private static final String SPRING_PASSWORD2_PROP_NM = "spring.data.gemfire.security.cluster2.password";;

    private static  MultiClusterAuthInit singleton;
    private Logger logger = LoggerFactory.getLogger(MultiClusterAuthInit.class);
    private ApplicationContext applicationContext;
    private Environment environment;
    private Properties secondaryProperties;
    private Properties gfSecurityProperties;

    protected MultiClusterAuthInit()
    {
    }
    @Override
    public Properties getCredentials(Properties securityProps, DistributedMember server, boolean isPeer) throws AuthenticationFailedException {

        var serverId = server.getId();
        logger.info("serverId: {}",serverId);


        Properties credentials =  null;
        if(isSecondaryCluster(server))
            credentials = getSecondaryCredentials();
        else
            credentials = getPrimaryCredentials();

        Properties finalCredentials = credentials;
        securityProps.forEach((k, v) -> {
            finalCredentials.setProperty((String) k, (String) v);
        });

        return finalCredentials;
    }

    private Properties getPrimaryCredentials() {
        if(this.gfSecurityProperties == null){
            this.gfSecurityProperties = new Properties();

            //set username
            this.gfSecurityProperties.setProperty(
                    SECURITY_USERNAME_PROP_NM,environment.getProperty(SPRING_USERNAME_PROP_NM));

            //set password

            this.gfSecurityProperties.setProperty(
                    SECURITY_PASSWORD_PROP_NM,environment.getProperty(SPRING_PASSWORD_PROP_NM));

        }

        return this.gfSecurityProperties;

    }

    private Properties getSecondaryCredentials() {
        if(this.secondaryProperties == null){
            this.secondaryProperties = new Properties();

            //set username
            this.secondaryProperties.setProperty(
                    SECURITY_USERNAME_PROP_NM,environment.getProperty(SPRING_USERNAME2_PROP_NM));

            //set password

            this.secondaryProperties.setProperty(
                    SECURITY_PASSWORD_PROP_NM,environment.getProperty(SPRING_PASSWORD2_PROP_NM));

        }

        return this.secondaryProperties;

    }

    protected boolean isSecondaryCluster(DistributedMember server) {

        String secondaryRegExp = environment.getProperty(SPRING_CLUSTER_2_REGEXP_PROP_NM);

        logger.info("secondaryRegExp: {}",secondaryRegExp);
        var memberId = server.getId();
        logger.info("memberId: {}",memberId);

        if(secondaryRegExp == null || secondaryRegExp.length() == 0)
        {

            logger.warn("Not checking if DistributedMember.id:{} is in the secondary cluster, does the Spring property \"{}\" is not set. ",memberId,SPRING_CLUSTER_2_REGEXP_PROP_NM, memberId);

            return false;
        }




        boolean isSecondary = memberId.matches(secondaryRegExp);

        logger.info("Is secondary:"+isSecondary);

        return isSecondary;
    }

    public static synchronized MultiClusterAuthInit instance()
    {
        if(singleton == null)
        {
            singleton = new MultiClusterAuthInit();
        }

        return singleton;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
