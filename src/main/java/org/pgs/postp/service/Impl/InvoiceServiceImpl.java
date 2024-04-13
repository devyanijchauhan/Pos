package org.pgs.postp.service.Impl;


import org.pgs.postp.dto.InvoiceDTO;
import org.pgs.postp.mapper.InvoiceMapper;
import org.pgs.postp.model.Cart;
import org.pgs.postp.model.InvoiceModel;
import org.pgs.postp.model.ProductModel;
import org.pgs.postp.model.VoucherModel;
import org.pgs.postp.repository.CartRepository;
import org.pgs.postp.repository.InvoiceRepository;
import org.pgs.postp.repository.ProductRepository;
import org.pgs.postp.repository.VoucherRepository;
import org.pgs.postp.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
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

    private final ProductRepository productRepository;

    private final VoucherRepository voucherRepository;

    @Autowired
    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, InvoiceMapper invoiceMapper, CartRepository cartRepository, ProductRepository productRepository, VoucherRepository voucherRepository) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.voucherRepository = voucherRepository;
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
    
    @Override
    public InvoiceDTO createInvoice(InvoiceDTO invoiceDTO) {
        InvoiceModel invoice = invoiceMapper.toEntity(invoiceDTO);
        calculateTotalPrice(invoice);

        // Check if a voucher is applied
        if (invoice.getVoucher() != null && !invoice.getVoucher().isEmpty()) {
            // Retrieve the voucher from the repository
            VoucherModel voucher = voucherRepository.findByVoucherCode(invoice.getVoucher());
            if (voucher != null && voucher.getValidForNumberOfCustomers() > 0) {
                // Decrease the remaining count for valid number of customers
                voucher.setValidForNumberOfCustomers(voucher.getValidForNumberOfCustomers() - 1);
                // Save the updated voucher
                voucherRepository.save(voucher);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid voucher code or no remaining uses.");
            }
        }

        InvoiceModel savedInvoice = invoiceRepository.save(invoice);
        updateProductQuantities(invoiceDTO.getCartData());
        return invoiceMapper.toDTO(savedInvoice);
    }

    public void updateProductQuantities(List<Cart> cartItems) {
        for (Cart cartItem : cartItems) {
            Long productId = cartItem.getProductId();
            BigDecimal quantity = cartItem.getQuantity();

            ProductModel product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

            BigDecimal currentStock = product.getStockQuantity();
            BigDecimal updatedStock = currentStock.subtract(quantity);
            if (updatedStock.compareTo(BigDecimal.ZERO) < 0) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }

            product.setStockQuantity(updatedStock);
            productRepository.save(product);
        }
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


        calculateTotalPrice(existingInvoice);


        InvoiceModel updatedInvoice = invoiceRepository.save(existingInvoice);
        return invoiceMapper.toDTO(updatedInvoice);
    }

    private void calculateTotalPrice(InvoiceModel invoice) {
        long totalMRP = invoice.getTotalMRP() != null ? invoice.getTotalMRP() : 0;
        long totalTax = invoice.getTotalTax() != null ? invoice.getTotalTax() : 0;
        long totalDiscount = invoice.getTotalDiscount() != null ? invoice.getTotalDiscount() : 0;


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


    @Override
    public long getInvoiceCount() {
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
        LocalDate startDate = LocalDate.now().with(DayOfWeek.MONDAY);

        LocalDate endDate = startDate.plusDays(6);


        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        int totalInvoices = invoiceRepository.countByCreatedAtBetween(startDateTime, endDateTime);

        return totalInvoices;
    }

    //for particular week
    @Override
    public int getTotalInvoicesCreatedInCurrentWeek() {
        LocalDate currentDate = LocalDate.now();

        LocalDate startDate = currentDate.with(DayOfWeek.MONDAY);

        LocalDate endDate = startDate.plusDays(6);

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);
        return invoiceRepository.countByCreatedAtBetween(startDateTime, endDateTime);
    }

    //for particular month
    @Override
    public int getTotalInvoicesCreatedInCurrentMonth() {
        LocalDate currentDate = LocalDate.now();

        LocalDate startDate = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), 1);

        LocalDate endDate = YearMonth.from(currentDate).atEndOfMonth();

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        return invoiceRepository.countByCreatedAtBetween(startDateTime, endDateTime);
    }

    @Override
    public Long getTotalMRPForCurrentWeek() {

        LocalDate currentDate = LocalDate.now();

        LocalDate startDate = currentDate.with(DayOfWeek.MONDAY);

        LocalDate endDate = startDate.plusDays(6);

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        return invoiceRepository.getTotalMRPForCurrentWeek(startDateTime, endDateTime);
    }


    @Override
    public Long getTotalTaxForCurrentWeek() {
        LocalDate currentDate = LocalDate.now();

        LocalDate startDate = currentDate.with(DayOfWeek.MONDAY);


        LocalDate endDate = startDate.plusDays(6);

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);
        return invoiceRepository.getTotalTaxForCurrentWeek(startDateTime, endDateTime);
    }

    @Override
    public Long getTotalDiscountForCurrentWeek() {
        LocalDate currentDate = LocalDate.now();

        LocalDate startDate = currentDate.with(DayOfWeek.MONDAY);

        LocalDate endDate = startDate.plusDays(6);

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);
        return invoiceRepository.getTotalDiscountForCurrentWeek(startDateTime, endDateTime);
    }

    @Override
    public Long getTotalPriceForCurrentWeek() {
        LocalDate currentDate = LocalDate.now();

        LocalDate startDate = currentDate.with(DayOfWeek.MONDAY);

        LocalDate endDate = startDate.plusDays(6);

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);
        return invoiceRepository.getTotalPriceForCurrentWeek(startDateTime, endDateTime);
    }


    @Override
    public Long getTotalMRPForCurrentMonth() {
        LocalDate currentDate = LocalDate.now();

        LocalDate startDate = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), 1);

        LocalDate endDate = YearMonth.from(currentDate).atEndOfMonth();

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        return invoiceRepository.getTotalMRPForCurrentMonth(startDateTime, endDateTime);
    }


    @Override
    public Long getTotalTaxForCurrentMonth() {
        LocalDate currentDate = LocalDate.now();

        LocalDate startDate = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), 1);

        LocalDate endDate = YearMonth.from(currentDate).atEndOfMonth();

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        return invoiceRepository.getTotalTaxForCurrentMonth(startDateTime, endDateTime);
    }

    @Override
    public Long getTotalDiscountForCurrentMonth() {
        LocalDate currentDate = LocalDate.now();

        LocalDate startDate = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), 1);

        LocalDate endDate = YearMonth.from(currentDate).atEndOfMonth();

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        return invoiceRepository.getTotalDiscountForCurrentMonth(startDateTime, endDateTime);
    }

    @Override
    public Long getTotalPriceForCurrentMonth() {
        LocalDate currentDate = LocalDate.now();

        LocalDate startDate = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), 1);

        LocalDate endDate = YearMonth.from(currentDate).atEndOfMonth();

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        return invoiceRepository.getTotalPriceForCurrentMonth(startDateTime, endDateTime);
    }

}