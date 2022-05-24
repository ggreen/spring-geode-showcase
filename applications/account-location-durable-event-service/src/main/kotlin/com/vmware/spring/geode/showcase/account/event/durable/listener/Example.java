//package com.vmware.spring.geode.showcase.account.event.durable.listener;
//
//import com.vmware.spring.geode.showcase.account.domain.account.Account;
//import org.apache.geode.cache.Region;
//import org.apache.geode.cache.client.ClientCache;
//import org.apache.geode.cache.client.ClientCacheFactory;
//import org.apache.geode.cache.client.ClientRegionShortcut;
//import org.apache.geode.cache.query.*;
//import org.apache.geode.pdx.PdxSerializer;
//import org.apache.geode.pdx.ReflectionBasedAutoSerializer;
//
//public class Example
//{
//
//    private ClientCache cache;
//    private Region<String, Account> accountRegion;
//    private CqQuery accountCqQuery;
//    private boolean durable = false;
//
//    private void init() throws CqException, RegionNotFoundException, CqExistsException
//    {
//        // init cache, region, and CQ
//
//        // connect to the locator using default port 10334
//        this.cache = connectToLocallyRunningGeode();
//
//
//        // create a local region that matches the server region
//        this.accountRegion = cache.<String, Account>createClientRegionFactory(ClientRegionShortcut.PROXY)
//                                  .create("Account");
//
//        this.accountCqQuery = this.startCQ(this.cache, this.accountRegion);
//
//        this.cache.readyForEvents();
//    }
//
//
//    private void close() throws CqException {
//
//        // close the CQ and Cache
//        this.accountCqQuery.close();
//        this.cache.close();
//
//    }
//
//
//    public static void main(String[] args) throws Exception {
//
//        Example example = new Example();
//
//        example.init();
//
//
//        while (true)
//        {
//            try{
//
//                Thread.sleep(10000);
//            }
//            catch(Exception e){}
//        }
//    }
//
//    private CqQuery startCQ(ClientCache cache, Region region)
//    throws CqException, RegionNotFoundException, CqExistsException {
//        // Get cache and queryService - refs to local cache and QueryService
//
//        CqAttributesFactory cqf = new CqAttributesFactory();
////        cqf.addCqListener(new VMwareAccountListener());
//        CqAttributes cqa = cqf.create();
//
//        String cqName = "accountVMwareTracker";
//
//        String queryStr = "select * from /Account";
//
//        QueryService queryService = region.getRegionService().getQueryService();
//
//        CqQuery cqQuery = queryService.newCq(cqName, queryStr, cqa,durable);
//        cqQuery.execute();
//
//
//        System.out.println("------- CQ is running\n");
//
//        return cqQuery;
//    }
//
//
//    private ClientCache connectToLocallyRunningGeode() {
//
//        PdxSerializer serializer = new ReflectionBasedAutoSerializer(".*");
//        ClientCache cache = new ClientCacheFactory().
//                addPoolLocator("127.0.0.1", 10334)
//                .setPdxSerializer(serializer)
//                .set("durable-client-id","example")
//                .set("durable-client-timeout","9999")
//                .setPoolSubscriptionEnabled(true)
//                .set("log-level", "WARN")
//                .create();
//
//
//        return cache;
//    }
//}
