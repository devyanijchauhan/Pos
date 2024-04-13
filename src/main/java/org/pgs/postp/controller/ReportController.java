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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@RestController
@PreAuthorize("hasAuthority('ADMIN')")
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
        Long totalMRP = invoiceService.getTotalMRP();
        reportDTO.setTotalMRP(totalMRP != null ? totalMRP : 0);
        Long totalTax = invoiceService.getTotalTax();
        reportDTO.setTotalTax(totalTax != null ? totalTax : 0);
        Long totalDiscount = invoiceService.getTotalDiscount();
        reportDTO.setTotalDiscount(totalDiscount != null ? totalDiscount : 0);
        Long totalPrice = invoiceService.getTotalPrice();
        reportDTO.setTotalPrice(totalPrice != null ? totalPrice : 0);

        // Weekly invoice data for the current week
        int totalInvoicesForWeek = invoiceService.getTotalInvoicesCreatedInCurrentWeek();
        reportDTO.setTotalInvoicesForWeek(totalInvoicesForWeek);
        Long totalMRPForWeek = invoiceService.getTotalMRPForCurrentWeek();
        reportDTO.setTotalMRPForWeek(totalMRPForWeek != null ? totalMRPForWeek : 0);
        Long totalTaxForWeek = invoiceService.getTotalTaxForCurrentWeek();
        reportDTO.setTotalTaxForWeek(totalTaxForWeek != null ? totalTaxForWeek : 0);
        Long totalDiscountForWeek = invoiceService.getTotalDiscountForCurrentWeek();
        reportDTO.setTotalDiscountForWeek(totalDiscountForWeek != null ? totalDiscountForWeek : 0);
        Long totalPriceForWeek = invoiceService.getTotalPriceForCurrentWeek();
        reportDTO.setTotalPriceForWeek(totalPriceForWeek != null ? totalPriceForWeek : 0);

        // Monthly invoice data for the current month
        int totalInvoicesForMonth = invoiceService.getTotalInvoicesCreatedInCurrentMonth();
        reportDTO.setTotalInvoicesForMonth(totalInvoicesForMonth);
        Long totalMRPForMonth = invoiceService.getTotalMRPForCurrentMonth();
        reportDTO.setTotalMRPForMonth(totalMRPForMonth != null ? totalMRPForMonth : 0);
        Long totalTaxForMonth = invoiceService.getTotalTaxForCurrentMonth();
        reportDTO.setTotalTaxForMonth(totalTaxForMonth != null ? totalTaxForMonth : 0);
        Long totalDiscountForMonth = invoiceService.getTotalDiscountForCurrentMonth();
        reportDTO.setTotalDiscountForMonth(totalDiscountForMonth != null ? totalDiscountForMonth : 0);
        Long totalPriceForMonth = invoiceService.getTotalPriceForCurrentMonth();
        reportDTO.setTotalPriceForMonth(totalPriceForMonth != null ? totalPriceForMonth : 0);


        return new ResponseEntity<>(reportDTO, HttpStatus.OK);
    }

}
