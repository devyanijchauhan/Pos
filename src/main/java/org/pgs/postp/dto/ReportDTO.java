package org.pgs.postp.dto;

import java.util.List;

public class ReportDTO {

//    private Long reportID;
    private long userCount;

    private long voucherCount;
    private long invoiceCount;
    private long totalMRP;
    private long totalTax;
    private long totalDiscount;
    private long totalPrice;
    private int totalInvoicesForWeek;
    private long totalMRPForWeek;
    private long totalTaxForWeek;
    private long totalDiscountForWeek;
    private long totalPriceForWeek;
    private int totalInvoicesForMonth;
    private long totalMRPForMonth;
    private long totalTaxForMonth;
    private long totalDiscountForMonth;
    private long totalPriceForMonth;


    public ReportDTO() {
    }

    public ReportDTO(
//            Long reportID,
                     long userCount, long voucherCount, long invoiceCount, long totalMRP,
                     long totalTax, long totalDiscount, long totalPrice, int totalInvoicesForWeek,
                     long totalMRPForWeek, long totalTaxForWeek, long totalDiscountForWeek,
                     long totalPriceForWeek, int totalInvoicesForMonth, long totalMRPForMonth,
                     long totalTaxForMonth, long totalDiscountForMonth, long totalPriceForMonth) {
//        this.reportID = reportID;
        this.userCount = userCount;
        this.voucherCount = voucherCount;
        this.invoiceCount = invoiceCount;
        this.totalMRP = totalMRP;
        this.totalTax = totalTax;
        this.totalDiscount = totalDiscount;
        this.totalPrice = totalPrice;
        this.totalInvoicesForWeek = totalInvoicesForWeek;
        this.totalMRPForWeek = totalMRPForWeek;
        this.totalTaxForWeek = totalTaxForWeek;
        this.totalDiscountForWeek = totalDiscountForWeek;
        this.totalPriceForWeek = totalPriceForWeek;
        this.totalInvoicesForMonth = totalInvoicesForMonth;
        this.totalMRPForMonth = totalMRPForMonth;
        this.totalTaxForMonth = totalTaxForMonth;
        this.totalDiscountForMonth = totalDiscountForMonth;
        this.totalPriceForMonth = totalPriceForMonth;
    }

//    public Long getReportID() {
//        return reportID;
//    }
//
//    public void setReportID(Long reportID) {
//        this.reportID = reportID;
//    }

    public long getUserCount() {
        return userCount;
    }

    public void setUserCount(long userCount) {
        this.userCount = userCount;
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

}
