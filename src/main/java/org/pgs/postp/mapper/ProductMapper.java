package org.pgs.postp.mapper;

import org.pgs.postp.model.ProductModel;
import org.pgs.postp.dto.ProductDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    // Convert ProductModel to ProductDTO
    public ProductDTO toDTO(ProductModel model) {
        ProductDTO dto = new ProductDTO();
        dto.setProductId(model.getProductId());
        dto.setName(model.getName());
        dto.setDescription(model.getDescription());
        dto.setPrice(model.getPrice());
        dto.setStockQuantity(model.getStockQuantity());
        dto.setSupplierID(model.getSupplier().getSupplierID());

        // You can map other fields here if needed
        return dto;
    }

    // Convert ProductDTO to ProductModel
    public static ProductModel toModel(ProductDTO dto) {
        ProductModel model = new ProductModel();
        model.setProductId(dto.getProductId());
        model.setName(dto.getName());
        model.setDescription(dto.getDescription());
        model.setPrice(dto.getPrice());
        // You can map other fields here if needed
        return model;
    }

    public ProductModel toEntity(ProductDTO productDTO) {
        if (productDTO == null) {
            return null;
        }
        ProductModel productModel = new ProductModel();
        productModel.setProductId(productDTO.getProductId());
        productModel.setName(productDTO.getName());
        productModel.setStockQuantity(productDTO.getStockQuantity());
        productModel.setDescription(productDTO.getDescription());
        productModel.setPrice(productDTO.getPrice());
        // You can map other fields here if needed
        return productModel;
    }
}
