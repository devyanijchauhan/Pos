package org.pgs.postp.controller;

import org.pgs.postp.dto.ReportDTO;
import org.pgs.postp.dto.UserDTO;
import org.pgs.postp.service.Impl.ReportServiceImpl;
import org.pgs.postp.service.InvoiceService;
import org.pgs.postp.service.ReportService;
import org.pgs.postp.service.UserService;
import org.pgs.postp.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/reports")
@CrossOrigin( origins = "http://Localhost:4200")
public class ReportController {

    private final ReportService reportService;

    private final ReportServiceImpl reportServiceImpl;

    private final UserService userService;

    private final VoucherService voucherService;

    private final InvoiceService invoiceService;

    @Autowired
    public ReportController(ReportService reportService, ReportServiceImpl reportServiceImpl, UserService userService, VoucherService voucherService, InvoiceService invoiceService) {
        this.reportService = reportService;
        this.reportServiceImpl = reportServiceImpl;
        this.userService = userService;
        this.voucherService = voucherService;
        this.invoiceService = invoiceService;
    }

    @GetMapping("/generateReport")
    public ResponseEntity<ReportDTO> generateReport() {

        ReportDTO reportDTO = new ReportDTO();

        // User-related data
        long userCount = userService.getUserCount();
        reportDTO.setUserCount(userCount);

        // Voucher-related data
        long voucherCount = voucherService.getVoucherCount();
        reportDTO.setVoucherCount(voucherCount);

        // Invoice-related data
        long invoiceCount = invoiceService.getInvoiceCount();
        reportDTO.setInvoiceCount(invoiceCount);
        long totalMRP = invoiceService.getTotalMRP();
        reportDTO.setTotalMRP(totalMRP);
        long totalTax = invoiceService.getTotalTax();
        reportDTO.setTotalTax(totalTax);
        long totalDiscount = invoiceService.getTotalDiscount();
        reportDTO.setTotalDiscount(totalDiscount);
        long totalPrice = invoiceService.getTotalPrice();
        reportDTO.setTotalPrice(totalPrice);

        // Weekly invoice data for the current week
        int totalInvoicesForWeek = invoiceService.getTotalInvoicesCreatedInCurrentWeek();
        reportDTO.setTotalInvoicesForWeek(totalInvoicesForWeek);
        Long totalMRPForWeek = invoiceService.getTotalMRPForCurrentWeek();
        reportDTO.setTotalMRPForWeek(totalMRPForWeek);
        Long totalTaxForWeek = invoiceService.getTotalTaxForCurrentWeek();
        reportDTO.setTotalTaxForWeek(totalTaxForWeek);
        Long totalDiscountForWeek = invoiceService.getTotalDiscountForCurrentWeek();
        reportDTO.setTotalDiscountForWeek(totalDiscountForWeek);
        Long totalPriceForWeek = invoiceService.getTotalPriceForCurrentWeek();
        reportDTO.setTotalPriceForWeek(totalPriceForWeek);

        // Monthly invoice data for the current month
        int totalInvoicesForMonth = invoiceService.getTotalInvoicesCreatedInCurrentMonth();
        reportDTO.setTotalInvoicesForMonth(totalInvoicesForMonth);
        Long totalMRPForMonth = invoiceService.getTotalMRPForCurrentMonth();
        reportDTO.setTotalMRPForMonth(totalMRPForMonth);
        Long totalTaxForMonth = invoiceService.getTotalTaxForCurrentMonth();
        reportDTO.setTotalTaxForMonth(totalTaxForMonth);
        Long totalDiscountForMonth = invoiceService.getTotalDiscountForCurrentMonth();
        reportDTO.setTotalDiscountForMonth(totalDiscountForMonth);
        Long totalPriceForMonth = invoiceService.getTotalPriceForCurrentMonth();
        reportDTO.setTotalPriceForMonth(totalPriceForMonth);

        return new ResponseEntity<>(reportDTO, HttpStatus.OK);
    }

}
