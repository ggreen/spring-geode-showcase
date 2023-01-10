package com.vmware.gemfire.multi.cluster.controller.security;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * GfSecuritiesEnvSpringLocator acts a bridge between Spring and GemFire
 * to provide the MultiClusterAuthInit with the Spring Environment object.
 *
 * @author gregory green
 */
@Component
public class GfSecuritiesEnvSpringLocator {
    private final Environment environment;

    public GfSecuritiesEnvSpringLocator(Environment environment) {
        this.environment = environment;

        MultiClusterAuthInit.instance().setEnvironment(environment);
    }
}
