package com.example.frontservice.transport;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class ApplicationEventListener {

    private final KafkaNewsConsumer consumer;

    public ApplicationEventListener(KafkaNewsConsumer consumer) {
        this.consumer = consumer;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void handleApplicationReadyEvent() {
        //consumer.consumeMessages();
    }
}
