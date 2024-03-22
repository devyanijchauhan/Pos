package org.pgs.postp.mapper;

import org.pgs.postp.model.SupplierModel;
import org.pgs.postp.dto.SupplierDTO;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class SupplierMapper {

    // Convert SupplierModel to SupplierDTO
    public SupplierDTO toDTO(SupplierModel model) {
        SupplierDTO dto = new SupplierDTO();
        dto.setSupplierID(model.getSupplierID());
        dto.setSupplierAgency(model.getSupplierAgency());
        dto.setContactPerson(model.getContactPerson());
        dto.setSupplierEmail(model.getSupplierEmail()); // Renamed from getName() to getEmail()
        dto.setSupplierPhone(model.getSupplierPhone()); // Renamed from getPhone() to getSupplierPhone()
        dto.setContactPersonEmail(model.getContactPersonEmail());
        dto.setContactPersonPhone(model.getContactPersonPhone());
        dto.setAddress(model.getAddress());
        return dto;
    }

    // Convert SupplierDTO to SupplierModel
    public SupplierModel toModel(SupplierDTO dto) {
        SupplierModel model = new SupplierModel();
        model.setSupplierID(dto.getSupplierID());
        model.setSupplierAgency(dto.getSupplierAgency());
        model.setContactPerson(dto.getContactPerson());
        model.setSupplierEmail(dto.getSupplierEmail()); // Renamed from setName() to setEmail()
        model.setSupplierPhone(dto.getSupplierPhone()); // Renamed from setPhone() to setSupplierPhone()
        model.setContactPersonEmail(dto.getContactPersonEmail());
        model.setContactPersonPhone(dto.getContactPersonPhone());
        model.setAddress(dto.getAddress());
        return model;
    }

    public SupplierModel toEntity(SupplierDTO supplierDTO) {
        if (supplierDTO == null) {
            return null;
        }
        SupplierModel supplierModel = new SupplierModel();
        supplierModel.setSupplierID(supplierDTO.getSupplierID());
        supplierModel.setSupplierAgency(supplierDTO.getSupplierAgency());
        supplierModel.setContactPerson(supplierDTO.getContactPerson());
        supplierModel.setSupplierEmail(supplierDTO.getSupplierEmail()); // Renamed from setName() to setEmail()
        supplierModel.setSupplierPhone(supplierDTO.getSupplierPhone()); // Renamed from setPhone() to setSupplierPhone()
        supplierModel.setContactPersonEmail(supplierDTO.getContactPersonEmail());
        supplierModel.setContactPersonPhone(supplierDTO.getContactPersonPhone());
        supplierModel.setAddress(supplierDTO.getAddress());
        return supplierModel;
    }

}
