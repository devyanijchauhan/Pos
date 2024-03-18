package org.pgs.postp.dto;

import java.math.BigInteger;

public class SupplierDTO {
    private Long supplierID;
    private String name;
    private String email;
    private BigInteger phone;

    // Constructors
    public SupplierDTO() {
    }

    public SupplierDTO(Long supplierID, String name, String email, BigInteger phone) {
        this.supplierID = supplierID;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    // Getters and Setters
    public Long getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(Long supplierID) {
        this.supplierID = supplierID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigInteger getPhone() {
        return phone;
    }

    public void setPhone(BigInteger phone) {
        this.phone = phone;
    }
}
