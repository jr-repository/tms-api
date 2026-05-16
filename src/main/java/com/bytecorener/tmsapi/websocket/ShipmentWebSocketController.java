package com.bytecorener.tmsapi.websocket;

import com.bytecorener.tmsapi.dto.LiveShipmentStatusMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ShipmentWebSocketController {
    private final LiveShipmentStatusPublisher publisher;

    @MessageMapping("/shipments/status")
    public void publishStatus(LiveShipmentStatusMessage message) {
        publisher.publish(message);
    }
}
