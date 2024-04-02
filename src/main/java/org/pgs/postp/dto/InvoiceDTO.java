package org.pgs.postp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class InvoiceDTO {
    private Long invoiceID;

    private LocalDateTime dateTime;
    private List<String> products;
    private String paymentMethod;
    private List<String> barcodeNumbers;
    private String customerName;
    private String customerPhone;
    private String voucher;
    private Long totalMRP;
    private Long totalTax;
    private Long totalDiscount;
    private Long totalPrice;
    private String status;

    private List<Object> cartData;

    // Constructors
    public InvoiceDTO() {
    }

    public InvoiceDTO(Long invoiceID, LocalDateTime dateTime, List<String> products, String paymentMethod, List<String> barcodeNumbers, String customerName, String customerPhone,
                      String voucher, Long totalMRP, Long totalTax, Long totalDiscount, Long totalPrice, String status, List<Object> cartData) {
        this.invoiceID = invoiceID;
        this.dateTime = dateTime;
        this.products = products;
        this.paymentMethod = paymentMethod;
        this.barcodeNumbers = barcodeNumbers;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.voucher = voucher;
        this.totalMRP = totalMRP;
        this.totalTax = totalTax;
        this.totalDiscount = totalDiscount;
        this.totalPrice = totalPrice;
        this.status = status;
        this.cartData = cartData;
    }

    // Getters and Setters
    public Long getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(Long invoiceID) {
        this.invoiceID = invoiceID;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<String> getBarcodeNumbers() {
        return barcodeNumbers;
    }

    public void setBarcodeNumbers(List<String> barcodeNumbers) {
        this.barcodeNumbers = barcodeNumbers;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }

    public Long getTotalMRP() {
        return totalMRP;
    }

    public void setTotalMRP(Long totalMRP) {
        this.totalMRP = totalMRP;
    }

    public Long getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(Long totalTax) {
        this.totalTax = totalTax;
    }

    public Long getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(Long totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Object> getCartData() {return cartData;}

    public void setCartData(List<Object> cartData) {this.cartData = cartData;}

}
