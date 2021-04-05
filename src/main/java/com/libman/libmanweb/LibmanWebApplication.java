package com.libman.libmanweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author manish
 *
 */
@SpringBootApplication
public class LibmanWebApplication extends SpringBootServletInitializer{


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(LibmanWebApplication.class);
    }
	public static void main(String[] args) {
		SpringApplication.run(LibmanWebApplication.class, args);
		System.out.print("Hello Manish");
	}

}
