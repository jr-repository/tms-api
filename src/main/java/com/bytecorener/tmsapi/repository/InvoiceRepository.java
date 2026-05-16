package com.bytecorener.tmsapi.repository;

import com.bytecorener.tmsapi.entity.Invoice;
import com.bytecorener.tmsapi.enumtype.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    long countByStatus(InvoiceStatus status);
}
