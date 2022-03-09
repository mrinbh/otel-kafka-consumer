package com.mrinal.otel.app1.db.respositories;

import com.mrinal.otel.app1.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findById(int id);

    List<Invoice> findByStatus(String status);
}