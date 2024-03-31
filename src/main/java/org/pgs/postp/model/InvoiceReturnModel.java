package org.pgs.postp.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "InvoiceReturns")
public class InvoiceReturnModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ReturnID")
    private Long returnID;

    @ManyToOne
    @JoinColumn(name = "InvoiceID", nullable = false)
    private InvoiceModel invoice;

    // Fields from InvoiceModel
    @Column(name = "DateTime", nullable = false)
    private LocalDateTime dateTime;

    @ElementCollection
    @CollectionTable(name = "Invoice_Products", joinColumns = @JoinColumn(name = "ReturnID"))
    @Column(name = "Product")
    private List<String> products = new ArrayList<>();

    @Column(name = "PaymentMethod")
    private String paymentMethod;

    @ElementCollection
    @CollectionTable(name = "Invoice_BarcodeNumbers", joinColumns = @JoinColumn(name = "ReturnID"))
    @Column(name = "BarcodeNumber")
    private List<String> barcodeNumbers = new ArrayList<>();

    @Column(name = "CustomerName")
    private String customerName;

    @Column(name = "CustomerPhone")
    private String customerPhone;

    @Column(name = "Voucher")
    private String voucher;

    @Column(name = "TotalMRP")
    private Long totalMRP;

    @Column(name = "TotalTax")
    private Long totalTax;

    @Column(name = "TotalDiscount")
    private Long totalDiscount;

    @Column(name = "TotalPrice")
    private Long totalPrice;

    @Column(name = "Status", nullable = false)
    private String status;


    @Column(name = "ReturnDate", nullable = false)
    private LocalDateTime returnDate;

    @Column(name = "ReturnReason")
    private String returnReason;


    // Constructors
    public InvoiceReturnModel() {
    }

    public InvoiceReturnModel(InvoiceModel invoice, LocalDateTime dateTime, List<String> products, String paymentMethod, List<String> barcodeNumbers, String customerName, String customerPhone, String voucher,
                              Long totalMRP, Long totalTax, Long totalDiscount, Long totalPrice, String status, LocalDateTime returnDate, String returnReason) {
        this.invoice = invoice;
        this.dateTime = invoice.getDateTime();
        this.products = invoice.getProducts();
        this.paymentMethod = invoice.getPaymentMethod();
        this.barcodeNumbers = invoice.getBarcodeNumbers();
        this.customerName = invoice.getCustomerName();
        this.customerPhone = invoice.getCustomerPhone();
        this.voucher = invoice.getVoucher();
        this.totalMRP = invoice.getTotalMRP();
        this.totalTax = invoice.getTotalTax();
        this.totalDiscount = invoice.getTotalDiscount();
        this.totalPrice = invoice.getTotalPrice();
        this.status = invoice.getStatus();
        this.returnDate = returnDate;
        this.returnReason = returnReason;
    }

    // Getters and Setters
    public Long getReturnID() {
        return returnID;
    }

    public void setReturnID(Long returnID) {
        this.returnID = returnID;
    }

    public InvoiceModel getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceModel invoice) {
        this.invoice = invoice;
    }

    // Getters and setters for fields from InvoiceModel

    public LocalDateTime getDateTime() {return dateTime;}

    public void setDateTime(LocalDateTime dateTime) {this.dateTime = dateTime;}

    public List<String> getProducts() {return products;}

    public void setProducts(List<String> products) {this.products = products;}

    public String getPaymentMethod() {return paymentMethod;}

    public void setPaymentMethod(String paymentMethod) {this.paymentMethod = paymentMethod;}

    public List<String> getBarcodeNumbers() {return barcodeNumbers;}

    public void setBarcodeNumbers(List<String> barcodeNumbers) {this.barcodeNumbers = barcodeNumbers;}

    public String getCustomerName() {return customerName;}

    public void setCustomerName(String customerName) {this.customerName = customerName;}

    public String getCustomerPhone() {return customerPhone;}

    public void setCustomerPhone(String customerPhone) {this.customerPhone = customerPhone;}

    public String getVoucher() {return voucher;}

    public void setVoucher(String voucher) {this.voucher = voucher;}

    public Long getTotalMRP() {return totalMRP;}

    public void setTotalMRP(Long totalMRP) {this.totalMRP = totalMRP;}

    public Long getTotalTax() {return totalTax;}

    public void setTotalTax(Long totalTax) {this.totalTax = totalTax;}

    public Long getTotalDiscount() {return totalDiscount;}

    public void setTotalDiscount(Long totalDiscount) {this.totalDiscount = totalDiscount;}

    public Long getTotalPrice() {return totalPrice;}

    public void setTotalPrice(Long totalPrice) {this.totalPrice = totalPrice;}

    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}

    public LocalDateTime getReturnDate() {return returnDate;}

    public void setReturnDate(LocalDateTime returnDate) {this.returnDate = returnDate;}

    public String getReturnReason() {return returnReason;}

    public void setReturnReason(String returnReason) {this.returnReason = returnReason;}

}
