package org.pgs.postp.service.Impl;

import org.pgs.postp.dto.SupplierDTO;
import org.pgs.postp.mapper.SupplierMapper;
import org.pgs.postp.model.CustomerModel;
import org.pgs.postp.model.SupplierModel;
import org.pgs.postp.repository.SupplierRepository;
import org.pgs.postp.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

        if (supplierDTO.getSupplierEmail() != null && supplierRepository.existsBySupplierEmail(supplierDTO.getSupplierEmail())) {
            throw new RuntimeException("Supplier email already exists: " + supplierDTO.getSupplierEmail());
        }

        if (supplierDTO.getSupplierPhone() != null && supplierRepository.existsBySupplierPhone(supplierDTO.getSupplierPhone())) {
            throw new RuntimeException(" Supplier phone number already exists: " + supplierDTO.getSupplierPhone());
        }

        if (supplierDTO.getContactPersonEmail() != null && supplierRepository.existsByContactPersonEmail(supplierDTO.getContactPersonEmail())) {
            throw new RuntimeException("Contact person email already exists: " + supplierDTO.getContactPersonEmail());
        }

        if (supplierDTO.getContactPersonPhone() != null && supplierRepository.existsByContactPersonPhone(supplierDTO.getContactPersonPhone())) {
            throw new RuntimeException("Contact person phone number already exists: " + supplierDTO.getContactPersonPhone());
        }

        SupplierModel supplier = supplierMapper.toEntity(supplierDTO);
        SupplierModel savedSupplier = supplierRepository.save(supplier);
        return supplierMapper.toDTO(savedSupplier);
    }

    @Override
    public SupplierDTO updateSupplier(Long id, SupplierDTO supplierDTO) {
        SupplierModel existingSupplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + id));


        if(supplierDTO.getSupplierAgency()!=null){
            existingSupplier.setSupplierAgency(supplierDTO.getSupplierAgency());
        }
        if(supplierDTO.getContactPerson()!=null){
            existingSupplier.setContactPerson(supplierDTO.getContactPerson());
        }
        if(supplierDTO.getSupplierEmail()!=null){
            existingSupplier.setSupplierEmail(supplierDTO.getSupplierEmail());
        }
        if(supplierDTO.getSupplierPhone()!=null){
            existingSupplier.setSupplierPhone(supplierDTO.getSupplierPhone());
        }
        if(supplierDTO.getContactPersonEmail()!=null){
            existingSupplier.setContactPersonEmail(supplierDTO.getContactPersonEmail());
        }
        if(supplierDTO.getContactPersonPhone()!=null){
            existingSupplier.setContactPersonPhone(supplierDTO.getContactPersonPhone());
        }

        if(supplierDTO.getAddress()!=null){
            existingSupplier.setAddress(supplierDTO.getAddress());
        }

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

    @Override
    public void processCSV(MultipartFile file) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));

        br.readLine();

        String line;

        List<SupplierModel> suppliersToAdd = new ArrayList<>();


        List<String> existingSupplierEmails = supplierRepository.findAll().stream()
                .map(SupplierModel::getSupplierEmail)
                .collect(Collectors.toList());
        List<BigInteger> existingSupplierPhones = supplierRepository.findAll().stream()
                .map(SupplierModel::getSupplierPhone)
                .collect(Collectors.toList());
        List<String> existingContactPersonEmails = supplierRepository.findAll().stream()
                .map(SupplierModel::getContactPersonEmail)

                .collect(Collectors.toList());
        List<BigInteger> existingContactPersonPhones = supplierRepository.findAll().stream()
                .map(SupplierModel::getContactPersonPhone)
                .collect(Collectors.toList());


        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            String supplierAgency = data[0].trim();
            String contactPerson = data[1].trim();
            String supplierEmail = data[2].trim();
            BigInteger supplierPhone = new BigInteger(data[3].trim());
            String contactPersonEmail = data[4].trim();
            BigInteger contactPersonPhone = new BigInteger(data[5].trim());
            String address = data[6].trim();

            if (existingSupplierEmails.contains(supplierEmail) || existingSupplierPhones.contains(supplierPhone) ||
                    existingContactPersonEmails.contains(contactPersonEmail) || existingContactPersonPhones.contains(contactPersonPhone)) {
                System.out.println("Duplicate email or phone found in CSV, skipping record: " + supplierEmail + " / " + supplierPhone + contactPersonEmail + " / " + contactPersonPhone);
                continue;
            }


            SupplierModel supplierModel = new SupplierModel();
            supplierModel.setSupplierAgency(supplierAgency);
            supplierModel.setContactPerson(contactPerson);
            supplierModel.setSupplierEmail(supplierEmail);
            supplierModel.setSupplierPhone(supplierPhone);
            supplierModel.setContactPersonEmail(contactPersonEmail);
            supplierModel.setContactPersonPhone(contactPersonPhone);
            supplierModel.setAddress(address);

            suppliersToAdd.add(supplierModel);
            existingSupplierEmails.add(supplierEmail);
            existingSupplierPhones.add(supplierPhone);
            existingContactPersonEmails.add(contactPersonEmail);
            existingContactPersonPhones.add(contactPersonPhone);

        }

        supplierRepository.saveAll(suppliersToAdd);
        br.close();
    }


}