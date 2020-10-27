package br.com.softdesign.douglasgiordano.pollingsessionmanager;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PollingSessionManagerApplication {

    public static final String queueName = "polling-session-result";

    public static void main(String[] args) {
        SpringApplication.run(PollingSessionManagerApplication.class, args);
    }

    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

}
