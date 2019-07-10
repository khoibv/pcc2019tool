package vn.nev.tools.pcctool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class PcctoolWebApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(PcctoolWebApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(PcctoolWebApplication.class);
	}
}
