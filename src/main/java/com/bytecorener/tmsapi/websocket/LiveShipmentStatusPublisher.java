package com.bytecorener.tmsapi.websocket;

import com.bytecorener.tmsapi.dto.LiveShipmentStatusMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LiveShipmentStatusPublisher {
    private final SimpMessagingTemplate messagingTemplate;

    public void publish(LiveShipmentStatusMessage message) {
        messagingTemplate.convertAndSend("/topic/shipments/" + message.shipmentOrderId(), message);
        messagingTemplate.convertAndSend("/topic/shipments", message);
    }
}
