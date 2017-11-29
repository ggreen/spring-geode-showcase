package io.pivotal.orders.gemfiredemo.listener;

import org.apache.geode.cache.query.CqEvent;
import org.springframework.data.gemfire.listener.ContinuousQueryListener;

public class OrderListener implements ContinuousQueryListener {

	@Override
	public void onEvent(CqEvent cqevent)
	{
		System.out.println("***** OrderListener ORDER KEY "+cqevent.getKey()+" *****");
		
	}
    

}
