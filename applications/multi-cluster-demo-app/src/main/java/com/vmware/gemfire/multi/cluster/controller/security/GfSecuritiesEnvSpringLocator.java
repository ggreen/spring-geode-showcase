package com.vmware.gemfire.multi.cluster.controller.security;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class GfSecuritiesEnvSpringLocator {
    private final Environment environment;

    public GfSecuritiesEnvSpringLocator(Environment environment) {
        this.environment = environment;

        MultiClusterAuthInit.instance().setEnvironment(environment);
    }
}
