package org.pgs.postp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "Reports")
public class ReportModel {



    @Column(name = "VoucherCount")
    @JsonProperty("voucher_count")
    private long voucherCount;

    @Column(name = "InvoiceCount")
    @JsonProperty("invoice_count")
    private long invoiceCount;

    @Column(name = "TotalMRP")
    @JsonProperty("total_mrp")
    private long totalMRP;

    @Column(name = "TotalTax")
    @JsonProperty("total_tax")
    private long totalTax;

    @Column(name = "TotalDiscount")
    @JsonProperty("total_discount")
    private long totalDiscount;

    @Column(name = "TotalPrice")
    @JsonProperty("total_price")
    private long totalPrice;

    @Column(name = "TotalInvoicesForWeek")
    @JsonProperty("total_invoices_for_week")
    private int totalInvoicesForWeek;

    @Column(name = "TotalMRPForWeek")
    @JsonProperty("total_mrp_for_week")
    private long totalMRPForWeek;

    @Column(name = "TotalTaxForWeek")
    @JsonProperty("total_tax_for_week")
    private long totalTaxForWeek;

    @Column(name = "TotalDiscountForWeek")
    @JsonProperty("total_discount_for_week")
    private long totalDiscountForWeek;

    @Column(name = "TotalPriceForWeek")
    @JsonProperty("total_price_for_week")
    private long totalPriceForWeek;

    @Column(name = "TotalInvoicesForMonth")
    @JsonProperty("total_invoices_for_month")
    private int totalInvoicesForMonth;

    @Column(name = "TotalMRPForMonth")
    @JsonProperty("total_mrp_for_month")
    private long totalMRPForMonth;

    @Column(name = "TotalTaxForMonth")
    @JsonProperty("total_tax_for_month")
    private long totalTaxForMonth;

    @Column(name = "TotalDiscountForMonth")
    @JsonProperty("total_discount_for_month")
    private long totalDiscountForMonth;

    @Column(name = "TotalPriceForMonth")
    @JsonProperty("total_price_for_month")
    private long totalPriceForMonth;

    @Id
    private Long id;

    public ReportModel() {
    }



    public long getVoucherCount() {
        return voucherCount;
    }

    public void setVoucherCount(long voucherCount) {
        this.voucherCount = voucherCount;
    }

    public long getInvoiceCount() {
        return invoiceCount;
    }

    public void setInvoiceCount(long invoiceCount) {
        this.invoiceCount = invoiceCount;
    }

    public long getTotalMRP() {
        return totalMRP;
    }

    public void setTotalMRP(long totalMRP) {
        this.totalMRP = totalMRP;
    }

    public long getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(long totalTax) {
        this.totalTax = totalTax;
    }

    public long getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(long totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalInvoicesForWeek() {
        return totalInvoicesForWeek;
    }

    public void setTotalInvoicesForWeek(int totalInvoicesForWeek) {
        this.totalInvoicesForWeek = totalInvoicesForWeek;
    }

    public long getTotalMRPForWeek() {
        return totalMRPForWeek;
    }

    public void setTotalMRPForWeek(long totalMRPForWeek) {
        this.totalMRPForWeek = totalMRPForWeek;
    }

    public long getTotalTaxForWeek() {
        return totalTaxForWeek;
    }

    public void setTotalTaxForWeek(long totalTaxForWeek) {
        this.totalTaxForWeek = totalTaxForWeek;
    }

    public long getTotalDiscountForWeek() {
        return totalDiscountForWeek;
    }

    public void setTotalDiscountForWeek(long totalDiscountForWeek) {
        this.totalDiscountForWeek = totalDiscountForWeek;
    }

    public long getTotalPriceForWeek() {
        return totalPriceForWeek;
    }

    public void setTotalPriceForWeek(long totalPriceForWeek) {
        this.totalPriceForWeek = totalPriceForWeek;
    }

    public int getTotalInvoicesForMonth() {
        return totalInvoicesForMonth;
    }

    public void setTotalInvoicesForMonth(int totalInvoicesForMonth) {
        this.totalInvoicesForMonth = totalInvoicesForMonth;
    }

    public long getTotalMRPForMonth() {
        return totalMRPForMonth;
    }

    public void setTotalMRPForMonth(long totalMRPForMonth) {
        this.totalMRPForMonth = totalMRPForMonth;
    }

    public long getTotalTaxForMonth() {
        return totalTaxForMonth;
    }

    public void setTotalTaxForMonth(long totalTaxForMonth) {
        this.totalTaxForMonth = totalTaxForMonth;
    }

    public long getTotalDiscountForMonth() {
        return totalDiscountForMonth;
    }

    public void setTotalDiscountForMonth(long totalDiscountForMonth) {
        this.totalDiscountForMonth = totalDiscountForMonth;
    }

    public long getTotalPriceForMonth() {
        return totalPriceForMonth;
    }

    public void setTotalPriceForMonth(long totalPriceForMonth) {
        this.totalPriceForMonth = totalPriceForMonth;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
