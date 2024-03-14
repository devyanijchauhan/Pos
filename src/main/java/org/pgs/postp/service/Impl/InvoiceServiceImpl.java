package org.pgs.postp.service.Impl;


import org.pgs.postp.dto.InvoiceDTO;
import org.pgs.postp.mapper.InvoiceMapper;
import org.pgs.postp.model.InvoiceModel;
import org.pgs.postp.model.SupplierModel;
import org.pgs.postp.repository.InvoiceRepository;
import org.pgs.postp.repository.SupplierRepository;
import org.pgs.postp.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;

    private final SupplierRepository supplierRepository;

    @Autowired
    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, InvoiceMapper invoiceMapper, SupplierRepository supplierRepository) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
        this.supplierRepository = supplierRepository;
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
//        InvoiceModel savedInvoice = invoiceRepository.save(invoice);
//        return invoiceMapper.toDTO(savedInvoice);
//   }

    @Override
    public InvoiceDTO createInvoice(InvoiceDTO invoiceDTO) {
        if (invoiceDTO.getSupplierId() == null) {
            throw new IllegalArgumentException("Supplier must be provided");
        }

        // Fetch the supplier from the database
        SupplierModel supplier = supplierRepository.findById(invoiceDTO.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + invoiceDTO.getSupplierId()));

        // Create the InvoiceModel entity and set the supplier
        InvoiceModel invoice = new InvoiceModel(
                supplier,
                invoiceDTO.getTotalAmount(),
                invoiceDTO.getDueDate(),
                invoiceDTO.getStatus());

        // Save the invoice to the database
        InvoiceModel savedInvoice = invoiceRepository.save(invoice);
        return invoiceMapper.toDTO(savedInvoice);
    }


    @Override
    public InvoiceDTO updateInvoice(Long id, InvoiceDTO invoiceDTO) {
        InvoiceModel existingInvoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found with id: " + id));

        if(invoiceDTO.getTotalAmount()!=null){
            existingInvoice.setTotalAmount(invoiceDTO.getTotalAmount());
        }

        if(invoiceDTO.getDueDate()!=null){
            existingInvoice.setDueDate(invoiceDTO.getDueDate());
        }

        if(invoiceDTO.getStatus()!=null){
            existingInvoice.setStatus(invoiceDTO.getStatus());
        }

        // Update properties here
        InvoiceModel updatedInvoice = invoiceRepository.save(existingInvoice);
        return invoiceMapper.toDTO(updatedInvoice);
    }

    @Override
    public void deleteInvoice(Long id) {
        if (!invoiceRepository.existsById(id)) {
            throw new RuntimeException("Invoice not found with id: " + id);
        }
        invoiceRepository.deleteById(id);
    }
}
