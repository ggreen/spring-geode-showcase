package io.pivotal.service.dataTx.circuitBreakerDemoApp;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.NoAvailableLocatorsException;
import org.apache.geode.cache.client.NoAvailableServersException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Example REST enabled service to demonstrate
 * primary/secondary connections
 */
@RefreshScope
@RestController
public class DemoService
{
    @Autowired
    @Qualifier("test")
    Region<String,String> testRegion;


    @Autowired
    CircuitBreaker circuitBreaker;

    /**
     * Get a value based on a given key
     * @param key the test region key
     * @return the test region value
     * @throws Exception
     */
    @GetMapping("/key/{key}")
    public String GetTestData(@PathVariable String key)
    throws Exception
    {
        try{
            System.out.println("key:"+key);
            String out = testRegion.get(key);

            System.out.println("out:"+out);
            return out;
        }
        catch(NoAvailableServersException | NoAvailableLocatorsException  e){
            e.printStackTrace();

            circuitBreaker.openCircuit();
            throw e;
        }
    }//-------------------------------------------
}
