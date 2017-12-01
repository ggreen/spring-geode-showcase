package io.pivotal.gemfire.playground.orders;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.geode.cache.Region;
import org.apache.geode.pdx.JSONFormatter;
import org.apache.geode.pdx.PdxInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.gemfire.GemfireTemplate;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.gemfire.config.annotation.EnablePdx;
import org.springframework.data.gemfire.config.annotation.EnableSecurity;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import gedi.solutions.geode.io.Querier;
import io.pivotal.gemfire.playground.orders.dao.AccountCrudRepository;
import io.pivotal.gemfire.playground.orders.dao.OrderCrudRepository;
import io.pivotal.gemfire.playground.orders.domain.Account;
import io.pivotal.gemfire.playground.orders.domain.Order;
import nyla.solutions.core.util.Organizer;

@Controller
@EnableAutoConfiguration
@SpringBootApplication
@ClientCacheApplication(name = "DataGemFireApplication", logLevel = "error")
@EnableGemfireRepositories
@EnablePdx(serializerBeanName = "pdxSerializer")
@EnableEntityDefinedRegions(basePackages = "io.pivotal.gemfire.playground.orders.domain")
@EnableSecurity
public class GemfireDemoApplication {

	
	// Autowired beans (by name)
	@Resource(name="orderRegion")
	Region<String, Order> orderRegion;
	
	// Used pdx to JSON examples
	@Resource(name="orderRegion")
	Region<String, Object> orderPdxRegion;
	
	//GemFire Template usage examples
	@Resource
	GemfireTemplate orderTemplate;
	
	@Resource
	GemfireTemplate 	accountsTemplate;
	
	// CRUD Repository
	@Autowired
	OrderCrudRepository orderCrudRepos;
	
	@Autowired
	AccountCrudRepository accountCrudRepos;
	
	// HTTP Mappings
	
	@GetMapping("/order/{id}")
    @ResponseBody
    public Order findById(@PathVariable String id) {
        return orderCrudRepos.findById(id).get();
    }
	
	@PostMapping("/saveAccount")
	@ResponseBody
	public boolean saveAccount(@RequestBody Account account)
	{
		this.accountCrudRepos.save(account);
		return true;
	}
	
	@GetMapping("/orderPdx/{id}")
    @ResponseBody
    public String findByIdWithPdx(@PathVariable String id) {
		Object value = orderPdxRegion.get(id);
	
		if(PdxInstance.class.isAssignableFrom(value.getClass()))
		{
			PdxInstance pdx = (PdxInstance)value;
			
			return JSONFormatter.toJSON(pdx);
		}
		
		return null;
    }//------------------------------------------------
	
	@GetMapping("/order/symbol/{symbol}")
    @ResponseBody
    public Order findBySymbol(@PathVariable String symbol) {
        return orderCrudRepos.findBySymbol(symbol);
    }
	
	
	
	@PostMapping("/order")
	@ResponseBody
	public boolean saveOrder(@RequestBody Order order)
	{
	     orderCrudRepos.save(order); 
		 return true;
	}//------------------------------------------------
	@RequestMapping("savePdx")
	@ResponseBody
	public String savePdxInstance(@RequestBody String json)
	{
		 PdxInstance pdx = JSONFormatter.fromJSON(json);
		 this.orderPdxRegion.put(pdx.getField("id").toString(), pdx);
		 
		 return JSONFormatter.toJSON(pdx);
	}//------------------------------------------------
	
	@GetMapping("/select")
	@ResponseBody
	public Collection<Order> executeQuery(String query)
	{
		return Querier.query(query);
	}//------------------------------------------------
	
	@GetMapping("/orders/{page}/{size}")
	@ResponseBody
	public Collection<Order> getPage(@PathVariable int page,@PathVariable int size)
	{
		
		Set<String> keySet = this.orderRegion.keySetOnServer();
		
		if(keySet == null || keySet.isEmpty())
			return null;
		
		List<Collection<String>> pages = Organizer.toPages(keySet, size);
		
		Map<String,Order> map = this.orderRegion.getAll(pages.get(page));
		
		if(map == null || map.isEmpty())
			return null;
		
		return map.values();
	}//------------------------------------------------

	@PostMapping("/createOrder")
	@ResponseBody
	public boolean create(@RequestBody Order order)
	{
		this.orderTemplate.create(order.getId(), order);
		return true;
	}//------------------------------------------------
	
	
	
	@PostMapping("/batchOrders")
	@ResponseBody
	public int saveOrders(@RequestBody ArrayList<Order> orders)
	{
		this.orderCrudRepos.saveAll(orders);
		
		
		return orders.size();
	}//------------------------------------------------
	
	
	 @RequestMapping("/")
	    @ResponseBody
	    String home() {
	        return "Demo";
	    }
	 
	public static void main(String[] args) {
		SpringApplication.run(GemfireDemoApplication.class, args);
	}
}
