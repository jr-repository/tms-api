package com.bytecorener.tmsapi.queue;

import com.bytecorener.tmsapi.dto.NotificationMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotificationQueueConsumer {
    @RabbitListener(queues = "${app.rabbitmq.notification-queue}")
    public void consume(NotificationMessage message) {
        log.info("Shipment notification received: orderNumber={}, status={}", message.orderNumber(), message.status());
    }
}
