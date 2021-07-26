package com.vmware.spring.geode.transaction;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnableClusterDefinedRegions;
import org.springframework.data.gemfire.transaction.config.EnableGemfireCacheTransactions;

@ClientCacheApplication
@EnableClusterDefinedRegions
@Configuration
@EnableGemfireCacheTransactions
public class GeodeConf
{
}
