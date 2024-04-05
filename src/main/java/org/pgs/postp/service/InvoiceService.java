package org.pgs.postp.service;

import org.pgs.postp.dto.InvoiceDTO;
import org.pgs.postp.model.Cart;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface InvoiceService {
    List<InvoiceDTO> getAllInvoices();

    InvoiceDTO getInvoiceById(Long id);

    InvoiceDTO createInvoice(InvoiceDTO invoiceDTO);

    InvoiceDTO updateInvoice(Long id, InvoiceDTO invoiceDTO);

    void deleteInvoice(Long id);

    @Query("SELECT COUNT(u) FROM InvoiceModel u")
    long getInvoiceCount();

    // Method to calculate the sum of totalMRP from all invoices
    Long getTotalMRP();

    Long getTotalTax();

    Long getTotalDiscount();

    Long getTotalPrice();

    int getTotalInvoicesCreatedThisWeek();

    //particular week
    int getTotalInvoicesCreatedInCurrentWeek();

    // For a particular month
    int getTotalInvoicesCreatedInCurrentMonth();

    Long getTotalMRPForCurrentWeek();

    Long getTotalTaxForCurrentWeek();

    Long getTotalDiscountForCurrentWeek();

    Long getTotalPriceForCurrentWeek();

    Long getTotalMRPForCurrentMonth();

    // Get total tax for invoices created in a particular month
    Long getTotalTaxForCurrentMonth();

    // Get total discount for invoices created in a particular month
    Long getTotalDiscountForCurrentMonth();

    // Get total price for invoices created in a particular month
    Long getTotalPriceForCurrentMonth();

    // Method to update product quantities based on the items in the invoice
    void updateProductQuantities(List<Cart> cartItems);


}
