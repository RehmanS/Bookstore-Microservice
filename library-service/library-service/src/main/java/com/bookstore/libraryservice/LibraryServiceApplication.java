package com.bookstore.libraryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableFeignClients
@EnableAspectJAutoProxy(proxyTargetClass = true) // AOP ucun
public class LibraryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryServiceApplication.class, args);
	}

	/* NOTE: This class is necessary so that Spring can use the Retrieve Message Error Decoder we created.
	    Method : Feign client error handling */
//	@Bean
//	public ErrorDecoder errorDecoder(){
//		return new RetrieveMessageErrorDecoder();
//	}
//
//	@Bean
//	Logger.Level feignLoggerLevel(){
//		return Logger.Level.FULL;
//	}

	/* NOTE: Another method is the fault tolerance method.
	    At this time, even if an exception returns, the process does not stop and another process is executed.
	    Methods inside the feign client are annotated with CircuitBreaker.
	     This is called resilience. */

}
