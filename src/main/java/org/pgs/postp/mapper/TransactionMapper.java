package org.pgs.postp.mapper;

import org.pgs.postp.dto.TransactionDTO;
import org.pgs.postp.model.TransactionModel;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {


    public TransactionDTO toDTO(TransactionModel model) {
        TransactionDTO dto = new TransactionDTO();
        dto.setTransactionID(model.getTransactionID());
        dto.setUserID(model.getUser().getUserID());
        dto.setCustomerID(model.getId() != null ? model.getId().getId() : null);
        dto.setTotalAmount(model.getTotalAmount());
        dto.setPaymentMethod(model.getPaymentMethod());
        dto.setTransactionDate(model.getTransactionDate());

        return dto;
    }


    public static TransactionModel toEntity(TransactionDTO dto) {
        TransactionModel model = new TransactionModel();
        model.setTransactionID(dto.getTransactionID());

        model.setTotalAmount(dto.getTotalAmount());
        model.setPaymentMethod(dto.getPaymentMethod());
        model.setTransactionDate(dto.getTransactionDate());

        return model;
    }
}
