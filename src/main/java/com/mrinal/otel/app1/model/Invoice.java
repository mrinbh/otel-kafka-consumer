package com.mrinal.otel.app1.model;

import javax.persistence.*;

@Entity
@Table(name = "Invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "invoiceType")
    private String invoiceType;

    @Column(name = "tax")
    private int tax;

    @Column(name = "status")
    private String status;

    @Column(name = "description")
    private String description;

    public Invoice(String invoiceType, int tax, String description){
        this.invoiceType =invoiceType;
        this.tax = tax;
        this.description= description;
        this.status="Processed";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public int getTax() {
        return tax;
    }

    public void setTax(int tax) {
        this.tax = tax;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}