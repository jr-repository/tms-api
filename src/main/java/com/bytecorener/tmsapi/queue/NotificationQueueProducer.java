package com.bytecorener.tmsapi.queue;

import com.bytecorener.tmsapi.dto.NotificationMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationQueueProducer {
    private final RabbitTemplate rabbitTemplate;

    @Value("${app.rabbitmq.exchange}")
    private String exchange;

    @Value("${app.rabbitmq.notification-routing-key}")
    private String routingKey;

    public void send(NotificationMessage message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
