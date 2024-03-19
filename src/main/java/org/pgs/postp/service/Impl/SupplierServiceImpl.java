package org.pgs.postp.service.Impl;

import org.pgs.postp.dto.SupplierDTO;
import org.pgs.postp.mapper.SupplierMapper;
import org.pgs.postp.model.SupplierModel;
import org.pgs.postp.repository.SupplierRepository;
import org.pgs.postp.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, SupplierMapper supplierMapper) {
        this.supplierRepository = supplierRepository;
        this.supplierMapper = supplierMapper;
    }

    @Override
    public List<SupplierDTO> getAllSuppliers() {
        List<SupplierModel> suppliers = supplierRepository.findAll();
        return suppliers.stream()
                .map(supplierMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SupplierDTO getSupplierById(Long id) {
        SupplierModel supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + id));
        return supplierMapper.toDTO(supplier);
    }

    @Override
    public SupplierDTO createSupplier(SupplierDTO supplierDTO) {
        SupplierModel supplier = supplierMapper.toEntity(supplierDTO);
        SupplierModel savedSupplier = supplierRepository.save(supplier);
        return supplierMapper.toDTO(savedSupplier);
    }

    @Override
    public SupplierDTO updateSupplier(Long id, SupplierDTO supplierDTO) {
        SupplierModel existingSupplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + id));
        if(supplierDTO.getSupplierAgency()!=null){ // Updated field name
            existingSupplier.setSupplierAgency(supplierDTO.getSupplierAgency()); // Updated field name
        }
        if(supplierDTO.getContactPerson()!=null){ // Updated field name
            existingSupplier.setContactPerson(supplierDTO.getContactPerson()); // Updated field name
        }
        if(supplierDTO.getSupplierEmail()!=null){ // Updated field name
            existingSupplier.setSupplierEmail(supplierDTO.getSupplierEmail()); // Updated field name
        }
        if(supplierDTO.getSupplierPhone()!=null){ // Updated field name
            existingSupplier.setSupplierPhone(supplierDTO.getSupplierPhone()); // Updated field name
        }
        if(supplierDTO.getContactPersonEmail()!=null){ // Updated field name
            existingSupplier.setContactPersonEmail(supplierDTO.getContactPersonEmail()); // Updated field name
        }
        if(supplierDTO.getContactPersonPhone()!=null){ // Updated field name
            existingSupplier.setContactPersonPhone(supplierDTO.getContactPersonPhone()); // Updated field name
        }
        // Update properties here
        SupplierModel updatedSupplier = supplierRepository.save(existingSupplier);
        return supplierMapper.toDTO(updatedSupplier);
    }

    @Override
    public void deleteSupplier(Long id) {
        if (!supplierRepository.existsById(id)) {
            throw new RuntimeException("Supplier not found with id: " + id);
        }
        supplierRepository.deleteById(id);
    }
}