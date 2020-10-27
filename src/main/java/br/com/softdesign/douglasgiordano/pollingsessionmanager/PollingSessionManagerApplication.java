package br.com.softdesign.douglasgiordano.pollingsessionmanager;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
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
