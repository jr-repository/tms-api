package com.bytecorener.tmsapi.service.impl;

import com.bytecorener.tmsapi.exception.BadRequestException;
import com.bytecorener.tmsapi.exception.ResourceNotFoundException;
import com.bytecorener.tmsapi.repository.ShipmentOrderRepository;
import com.bytecorener.tmsapi.service.ReportService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final ShipmentOrderRepository shipmentOrderRepository;

    @Override
    public byte[] shipmentReport(Long shipmentOrderId) {
        var order = shipmentOrderRepository.findById(shipmentOrderId).orElseThrow(() -> new ResourceNotFoundException("Shipment order not found"));
        String template = """
                <?xml version="1.0" encoding="UTF-8"?>
                <jasperReport xmlns="http://jasperreports.sourceforge.net/jasperreports" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://jasperreports.sourceforge.net/jasperreports http://jasperreports.sourceforge.net/xsd/jasperreport.xsd" name="ShipmentReport" pageWidth="595" pageHeight="842" columnWidth="515" leftMargin="40" rightMargin="40" topMargin="40" bottomMargin="40">
                    <parameter name="orderNumber" class="java.lang.String"/>
                    <parameter name="customer" class="java.lang.String"/>
                    <parameter name="pickupAddress" class="java.lang.String"/>
                    <parameter name="deliveryAddress" class="java.lang.String"/>
                    <parameter name="status" class="java.lang.String"/>
                    <title><band height="80"><staticText><reportElement x="0" y="0" width="515" height="30"/><textElement><font size="18" isBold="true"/></textElement><text><![CDATA[TMS Shipment Report]]></text></staticText></band></title>
                    <detail><band height="180">
                        <textField><reportElement x="0" y="0" width="515" height="25"/><textFieldExpression><![CDATA["Order Number: " + $P{orderNumber}]]></textFieldExpression></textField>
                        <textField><reportElement x="0" y="30" width="515" height="25"/><textFieldExpression><![CDATA["Customer: " + $P{customer}]]></textFieldExpression></textField>
                        <textField><reportElement x="0" y="60" width="515" height="25"/><textFieldExpression><![CDATA["Pickup Address: " + $P{pickupAddress}]]></textFieldExpression></textField>
                        <textField><reportElement x="0" y="90" width="515" height="25"/><textFieldExpression><![CDATA["Delivery Address: " + $P{deliveryAddress}]]></textFieldExpression></textField>
                        <textField><reportElement x="0" y="120" width="515" height="25"/><textFieldExpression><![CDATA["Status: " + $P{status}]]></textFieldExpression></textField>
                    </band></detail>
                </jasperReport>
                """;
        Map<String, Object> params = new HashMap<>();
        params.put("orderNumber", order.getOrderNumber());
        params.put("customer", order.getCustomer().getCompanyName());
        params.put("pickupAddress", order.getPickupAddress());
        params.put("deliveryAddress", order.getDeliveryAddress());
        params.put("status", order.getStatus().name());
        try {
            JasperReport report = JasperCompileManager.compileReport(new ByteArrayInputStream(template.getBytes(StandardCharsets.UTF_8)));
            JasperPrint print = JasperFillManager.fillReport(report, params, new JREmptyDataSource());
            return JasperExportManager.exportReportToPdf(print);
        } catch (JRException ex) {
            throw new BadRequestException("Failed to generate PDF report");
        }
    }
}
