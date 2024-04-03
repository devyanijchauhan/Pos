package org.pgs.postp.service.Impl;


import org.pgs.postp.dto.InvoiceDTO;
import org.pgs.postp.mapper.InvoiceMapper;
import org.pgs.postp.model.Cart;
import org.pgs.postp.model.InvoiceModel;
import org.pgs.postp.repository.CartRepository;
import org.pgs.postp.repository.InvoiceRepository;
import org.pgs.postp.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;
    private final CartRepository cartRepository;

    @Autowired
    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, InvoiceMapper invoiceMapper, CartRepository cartRepository) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
        this.cartRepository = cartRepository;
    }

    @Override
    public List<InvoiceDTO> getAllInvoices() {
        List<InvoiceModel> invoices = invoiceRepository.findAll();
        return invoices.stream()
                .map(invoiceMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public InvoiceDTO getInvoiceById(Long id) {
        InvoiceModel invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found with id: " + id));
        return invoiceMapper.toDTO(invoice);
    }

//    @Override
//    public InvoiceDTO createInvoice(InvoiceDTO invoiceDTO) {
//        InvoiceModel invoice = invoiceMapper.toEntity(invoiceDTO);
//        Cart cart =invoiceDTO.getCartData();
//        Cart savedCart = cartRepository.save(cart);
//        calculateTotalPrice(invoice);
//        invoice.setCartData(savedCart);
//        InvoiceModel savedInvoice = invoiceRepository.save(invoice);
//        return invoiceMapper.toDTO(savedInvoice);
//    }
@Override
public InvoiceDTO createInvoice(InvoiceDTO invoiceDTO) {
    InvoiceModel invoice = invoiceMapper.toEntity(invoiceDTO);
    calculateTotalPrice(invoice);
    InvoiceModel savedInvoice = invoiceRepository.save(invoice);
    return invoiceMapper.toDTO(savedInvoice);
}

    @Override
    public InvoiceDTO updateInvoice(Long id, InvoiceDTO invoiceDTO) {
        InvoiceModel existingInvoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found with id: " + id));

        if (invoiceDTO.getDateTime() != null) {
            existingInvoice.setDateTime(invoiceDTO.getDateTime());
        }
        if (invoiceDTO.getProducts() != null) {
            existingInvoice.setProducts(invoiceDTO.getProducts());
        }
        if (invoiceDTO.getPaymentMethod() != null) {
            existingInvoice.setPaymentMethod(invoiceDTO.getPaymentMethod());
        }
        if (invoiceDTO.getBarcodeNumbers() != null) {
            existingInvoice.setBarcodeNumbers(invoiceDTO.getBarcodeNumbers());
        }
        if (invoiceDTO.getCustomerName() != null) {
            existingInvoice.setCustomerName(invoiceDTO.getCustomerName());
        }
        if (invoiceDTO.getCustomerPhone() != null) {
            existingInvoice.setCustomerPhone(invoiceDTO.getCustomerPhone());
        }
        if (invoiceDTO.getVoucher() != null) {
            existingInvoice.setVoucher(invoiceDTO.getVoucher());
        }
        if (invoiceDTO.getTotalMRP() != null) {
            existingInvoice.setTotalMRP(invoiceDTO.getTotalMRP());
        }
        if (invoiceDTO.getTotalTax() != null) {
            existingInvoice.setTotalTax(invoiceDTO.getTotalTax());
        }
        if (invoiceDTO.getTotalDiscount() != null) {
            existingInvoice.setTotalDiscount(invoiceDTO.getTotalDiscount());
        }
        if (invoiceDTO.getTotalPrice() != null) {
            existingInvoice.setTotalPrice(invoiceDTO.getTotalPrice());
        }

        if (invoiceDTO.getStatus() != null) {
            existingInvoice.setStatus(invoiceDTO.getStatus());
        }

        if (invoiceDTO.getCartData() != null) {
            existingInvoice.setCartData(invoiceDTO.getCartData());
        }

        // Calculate total price
        calculateTotalPrice(existingInvoice);

        // Update properties here
        InvoiceModel updatedInvoice = invoiceRepository.save(existingInvoice);
        return invoiceMapper.toDTO(updatedInvoice);
    }

    private void calculateTotalPrice(InvoiceModel invoice) {
        long totalMRP = invoice.getTotalMRP() != null ? invoice.getTotalMRP() : 0;
        long totalTax = invoice.getTotalTax() != null ? invoice.getTotalTax() : 0;
        long totalDiscount = invoice.getTotalDiscount() != null ? invoice.getTotalDiscount() : 0;

        // Calculate total price
        long totalPrice = totalMRP + totalTax - totalDiscount;
        invoice.setTotalPrice(totalPrice);
    }

    @Override
    public void deleteInvoice(Long id) {
        if (!invoiceRepository.existsById(id)) {
            throw new RuntimeException("Invoice not found with id: " + id);
        }
        invoiceRepository.deleteById(id);
    }

    // Implementation of the getInvoiceCount() method
    @Override
    public long getInvoiceCount() {
        // Your implementation to retrieve invoice count from the database
        return invoiceRepository.count();
    }

    @Override
    public Long getTotalMRP() {
        return invoiceRepository.getTotalMRP();
    }

    @Override
    public Long getTotalTax() {
        return invoiceRepository.getTotalTax();
    }

    @Override
    public Long getTotalDiscount() {
        return invoiceRepository.getTotalDiscount();
    }

    @Override
    public Long getTotalPrice() {
        return invoiceRepository.getTotalPrice();
    }

    @Override
    public int getTotalInvoicesCreatedThisWeek() {
        // Get the start date of the current week (assuming week starts on Monday)
        LocalDate startDate = LocalDate.now().with(DayOfWeek.MONDAY);

        // Get the end date of the current wee
        LocalDate endDate = startDate.plusDays(6);

        // Get the start and end of the current week as LocalDateTime for querying
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        // Query the database for invoices created within the current week
        int totalInvoices = invoiceRepository.countByCreatedAtBetween(startDateTime, endDateTime);

        return totalInvoices;
    }

    //for particular week (startDate to endDate)
    @Override
    public int getTotalInvoicesCreatedInWeek(LocalDateTime startDate, LocalDateTime endDate) {
        // Query the database for invoices created within the specified week
        return invoiceRepository.countByCreatedAtBetween(startDate, endDate);
    }

    //for particular month (startDate to endDate)
    @Override
    public int getTotalInvoicesCreatedInMonth(LocalDateTime startDate, LocalDateTime endDate) {
        // Query the database for invoices created within the specified month
        return invoiceRepository.countByCreatedAtBetween(startDate, endDate);
    }

    @Override
    public Long getTotalMRPForWeek(LocalDateTime startDate, LocalDateTime endDate) {
        Long totalMRP = invoiceRepository.getTotalMRPForWeek(startDate, endDate);
        // If totalMRP is zero, throw a custom exception
        if (totalMRP == 0) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No entries found for this week"
            );
        }

        return totalMRP;
    }


    @Override
    public Long getTotalTaxForWeek(LocalDateTime startDate, LocalDateTime endDate) {
        Long totalTax = invoiceRepository.getTotalTaxForWeek(startDate, endDate);
        // If totalTax is zero, throw a custom exception
        if (totalTax == 0) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No entries found for this week"
            );
        }

        return totalTax;
    }


    @Override
    public Long getTotalDiscountForWeek(LocalDateTime startDate, LocalDateTime endDate) {
        Long totalDiscount = invoiceRepository.getTotalDiscountForWeek(startDate, endDate);
        // If totalDiscount is zero, throw a custom exception
        if (totalDiscount == 0) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No entries found for this week"
            );
        }

        return totalDiscount;
    }


    @Override
    public Long getTotalPriceForWeek(LocalDateTime startDate, LocalDateTime endDate) {
        Long totalPrice = invoiceRepository.getTotalPriceForWeek(startDate, endDate);
        // If totalPrice is zero, throw a custom exception
        if (totalPrice == 0) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No entries found for this week"
            );
        }

        return totalPrice;
    }

    @Override
    public Long getTotalMRPForMonth(int year, int month) {
        LocalDateTime startDate = YearMonth.of(year, month).atDay(1).atStartOfDay();
        LocalDateTime endDate = YearMonth.of(year, month).atEndOfMonth().atTime(23, 59, 59);
        Long totalMRP = invoiceRepository.getTotalMRPForMonth(startDate, endDate);
        // If totalMRP is null, throw a custom exception
        if (totalMRP == 0) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No entry found for this month"
            );
        }

        return totalMRP;

    }

    @Override
    public Long getTotalTaxForMonth(int year, int month) {
        LocalDateTime startDate = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(year, month, startDate.toLocalDate().lengthOfMonth(), 23, 59, 59);
        Long totalTax = invoiceRepository.getTotalTaxForMonth(startDate, endDate);
        // If totalTax is null, throw a custom exception
        if (totalTax == 0) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No entry found for this month"
            );
        }

        return totalTax;

    }


    @Override
    public Long getTotalDiscountForMonth(int year, int month) {
        LocalDateTime startDate = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(year, month, startDate.toLocalDate().lengthOfMonth(), 23, 59, 59);
        Long totalDiscount = invoiceRepository.getTotalDiscountForMonth(startDate, endDate);
        // If totalDiscount is null, throw a custom exception
        if (totalDiscount == 0) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No entry found for this month"
            );
        }

        return totalDiscount;

    }

    @Override
    public Long getTotalPriceForMonth(int year, int month) {
        LocalDateTime startDate = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(year, month, startDate.toLocalDate().lengthOfMonth(), 23, 59, 59);
        Long totalPrice = invoiceRepository.getTotalPriceForMonth(startDate, endDate);
        // If totalPrice is null, throw a custom exception
        if (totalPrice == 0) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No entry found for this month"
            );
        }

        return totalPrice;

    }
}