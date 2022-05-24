//package com.vmware.spring.geode.showcase.account.event.durable.cacheListener;
//
//
//import static org.apache.geode.distributed.ConfigurationProperties.DURABLE_CLIENT_ID;
//import static org.apache.geode.distributed.ConfigurationProperties.DURABLE_CLIENT_TIMEOUT;
//import static org.apache.geode.distributed.ConfigurationProperties.LOG_LEVEL;
//
//import java.util.concurrent.CountDownLatch;
//
//import com.vmware.spring.geode.showcase.account.domain.account.Account;
//import com.vmware.spring.geode.showcase.account.domain.account.Location;
//import com.vmware.spring.geode.showcase.account.event.durable.listener.VMwareAccountListener;
//import org.apache.geode.cache.EntryEvent;
//import org.apache.geode.cache.InterestResultPolicy;
//import org.apache.geode.cache.Region;
//import org.apache.geode.cache.client.ClientCache;
//import org.apache.geode.cache.client.ClientCacheFactory;
//import org.apache.geode.cache.client.ClientRegionFactory;
//import org.apache.geode.cache.client.ClientRegionShortcut;
//import org.apache.geode.cache.util.CacheListenerAdapter;
//import org.apache.geode.pdx.ReflectionBasedAutoSerializer;
//
//public class Example {
//    private static final int numEvents = 10;
//    private static final CountDownLatch waitForEventsLatch = new CountDownLatch(numEvents);
//
//    public static void main(String[] args) throws Exception {
//        ClientCache clientCacheOne = createDurableClient();
//
//        final String regionName = "Account";
//
//        // Create a local caching proxy region that matches the server region
//        ClientRegionFactory<String, Account> clientOneRegionFactory =
//                clientCacheOne.createClientRegionFactory(ClientRegionShortcut.PROXY)
//                        .addCacheListener(new ExampleCacheListener());
//
//        Region<String, Account> accountRegion = clientOneRegionFactory.create(regionName);
//
//        // Register interest to create the durable client message queue
//        accountRegion.registerInterestForAllKeys(InterestResultPolicy.DEFAULT, true);
//
//
//
//        // Create a second client to do puts with while the first client is disconnected
//
//        ClientRegionFactory<String, Location> clientTwoRegionFactory =
//                clientCacheOne.createClientRegionFactory(ClientRegionShortcut.PROXY);
//        Region<String, Location> locationRegion = clientTwoRegionFactory.create("Location");
//
//
//        // Signal to the server that this client is ready to receive events.
//        // Events in this client's durable message queue
//        // will then be delivered and trigger our example cache listener.
//        clientCacheOne.readyForEvents();
//
//        // Use a count down latch to ensure that this client receives all queued events from the server
//        while(true)
//        {
//            Thread.sleep(10000000);
//        }
//    }
//
//    private static ClientCache createDurableClient() {
//        return new ClientCacheFactory().addPoolLocator("127.0.0.1", 10334)
//                                       // Provide a unique identifier for this client's durable subscription message queue
//                                       .set(DURABLE_CLIENT_ID, "1")
//                                       // Provide a timeout in seconds for how long the server will wait for the client to
//                                       // reconnect.
//                                       // If this property isn't set explicitly, it defaults to 300 seconds.
//                                       .set(DURABLE_CLIENT_TIMEOUT, "200")
//                                        .setPdxSerializer(new ReflectionBasedAutoSerializer(".*"))
//                                       // This is required so the client can register interest for all keys on this durable client
//                                       .setPoolSubscriptionEnabled(true).set(LOG_LEVEL, "WARN").create();
//    }
//
//    public static class ExampleCacheListener<String, Account>
//            extends CacheListenerAdapter<String, Account> {
//        public ExampleCacheListener() {}
//
//        @Override
//        public void afterUpdate(EntryEvent<String, Account> event)
//        {
//            afterCreate(event);
//        }
//
//
//        @Override
//        public void afterCreate(EntryEvent<String, Account> event) {
//
//            System.out.println("========== YOOYOYOY ================");
//            System.out.println(
//                    "Received create for key " + event.getKey() + " after durable client reconnection");
//            waitForEventsLatch.countDown();
//        }
//    }
//}