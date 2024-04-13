package org.pgs.postp.service;

import org.pgs.postp.dto.InvoiceDTO;
import org.pgs.postp.model.Cart;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvoiceService {
    List<InvoiceDTO> getAllInvoices();

    InvoiceDTO getInvoiceById(Long id);

    InvoiceDTO createInvoice(InvoiceDTO invoiceDTO);

    InvoiceDTO updateInvoice(Long id, InvoiceDTO invoiceDTO);

    void deleteInvoice(Long id);

    @Query("SELECT COUNT(u) FROM InvoiceModel u")
    long getInvoiceCount();


    Long getTotalMRP();

    Long getTotalTax();

    Long getTotalDiscount();

    Long getTotalPrice();

    int getTotalInvoicesCreatedThisWeek();


    int getTotalInvoicesCreatedInCurrentWeek();


    int getTotalInvoicesCreatedInCurrentMonth();

    Long getTotalMRPForCurrentWeek();

    Long getTotalTaxForCurrentWeek();

    Long getTotalDiscountForCurrentWeek();

    Long getTotalPriceForCurrentWeek();

    Long getTotalMRPForCurrentMonth();


    Long getTotalTaxForCurrentMonth();


    Long getTotalDiscountForCurrentMonth();

    Long getTotalPriceForCurrentMonth();


    void updateProductQuantities(List<Cart> cartItems);

}
