package com.bytecorener.tmsapi.mapper;

import com.bytecorener.tmsapi.dto.*;
import com.bytecorener.tmsapi.entity.*;
import org.springframework.stereotype.Component;

@Component
public class TmsMapper {
    public UserResponse toUserResponse(User user) {
        return new UserResponse(user.getId(), user.getFullName(), user.getEmail(), user.getRole(), user.isActive());
    }

    public VehicleResponse toVehicleResponse(Vehicle vehicle) {
        return new VehicleResponse(vehicle.getId(), vehicle.getPlateNumber(), vehicle.getVehicleType(), vehicle.getCapacityKg(), vehicle.getStatus());
    }

    public DriverResponse toDriverResponse(DriverProfile driver) {
        return driver == null ? null : new DriverResponse(driver.getId(), toUserResponse(driver.getUser()), driver.getLicenseNumber(), driver.getPhoneNumber(), driver.isAvailable());
    }

    public CustomerResponse toCustomerResponse(CustomerProfile customer) {
        return new CustomerResponse(customer.getId(), toUserResponse(customer.getUser()), customer.getCompanyName(), customer.getPhoneNumber(), customer.getAddress());
    }

    public ShipmentOrderResponse toShipmentOrderResponse(ShipmentOrder order) {
        return new ShipmentOrderResponse(order.getId(), order.getOrderNumber(), toCustomerResponse(order.getCustomer()), order.getPickupAddress(), order.getDeliveryAddress(), order.getCargoDescription(), order.getWeightKg(), order.getStatus(), order.getCreatedAt(), order.getUpdatedAt());
    }

    public TripResponse toTripResponse(Trip trip) {
        return new TripResponse(trip.getId(), toShipmentOrderResponse(trip.getShipmentOrder()), toDriverResponse(trip.getDriver()), trip.getVehicle() == null ? null : toVehicleResponse(trip.getVehicle()), trip.getPlannedPickupAt(), trip.getPlannedDeliveryAt(), trip.getStatus());
    }

    public CostResponse toCostResponse(Cost cost) {
        return new CostResponse(cost.getId(), cost.getShipmentOrder().getId(), cost.getDescription(), cost.getAmount());
    }

    public InvoiceResponse toInvoiceResponse(Invoice invoice) {
        return new InvoiceResponse(invoice.getId(), invoice.getInvoiceNumber(), invoice.getShipmentOrder().getId(), invoice.getTotalAmount(), invoice.getStatus(), invoice.getIssuedAt());
    }
}
