package org.pgs.postp.mapper;

import org.pgs.postp.dto.TransactionDetailDTO;
import org.pgs.postp.model.TransactionDetailModel;
import org.springframework.stereotype.Component;

@Component
public class TransactionDetailMapper {


    public TransactionDetailDTO toDTO(TransactionDetailModel model) {
        TransactionDetailDTO dto = new TransactionDetailDTO();
        dto.setTransactionDetailID(model.getTransactionDetailID());
        dto.setTransactionID(model.getTransaction().getTransactionID());
        dto.setProductID(model.getProduct().getProductId());
        dto.setQuantity(model.getQuantity());
        dto.setUnitPrice(model.getUnitPrice());
        dto.setDiscount(model.getDiscount());

        return dto;
    }


    public static TransactionDetailModel toEntity(TransactionDetailDTO dto) {
        TransactionDetailModel model = new TransactionDetailModel();
        model.setTransactionDetailID(dto.getTransactionDetailID());

        model.setQuantity(dto.getQuantity());
        model.setUnitPrice(dto.getUnitPrice());
        model.setDiscount(dto.getDiscount());

        return model;
    }
}
