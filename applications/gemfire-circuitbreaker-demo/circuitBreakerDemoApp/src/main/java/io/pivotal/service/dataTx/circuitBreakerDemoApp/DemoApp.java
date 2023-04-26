package io.pivotal.service.dataTx.circuitBreakerDemoApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Demo Spring Application
 */
@SpringBootApplication(scanBasePackageClasses = {AppConfig.class})
public class DemoApp
{

    public static void main(String[] args)
    {
        CircuitBreaker.setContext(
                SpringApplication.run(DemoApp.class, args));
    }
}
