package br.com.softdesign.douglasgiordano.pollingsessionmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
public class PollingSessionManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PollingSessionManagerApplication.class, args);
	}

	/**
	 * Task async executor
	 * @return Executor
	 */
	@Bean
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(2);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("PollingSession-");
		executor.initialize();
		return executor;
	}
}
