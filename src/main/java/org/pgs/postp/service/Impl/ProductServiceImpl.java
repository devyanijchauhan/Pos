package org.pgs.postp.service.Impl;

import org.pgs.postp.dto.ProductDTO;
import org.pgs.postp.mapper.ProductMapper;
import org.pgs.postp.model.ProductModel;
import org.pgs.postp.model.SupplierModel;
import org.pgs.postp.repository.ProductRepository;
import org.pgs.postp.repository.SupplierRepository;
import org.pgs.postp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final SupplierRepository supplierRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, SupplierRepository supplierRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.supplierRepository = supplierRepository;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<ProductModel> products = productRepository.findAll();
        return products.stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductById(Long id) {
        ProductModel product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        return productMapper.toDTO(product);
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        if (productDTO.getSupplierIds() == null || productDTO.getSupplierIds().isEmpty()) {
            throw new IllegalArgumentException("At least one supplier must be provided");
        }

        // Fetch the suppliers from the database
        List<SupplierModel> suppliers = productDTO.getSupplierIds().stream()
                .map(supplierId -> supplierRepository.findById(supplierId)
                        .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + supplierId)))
                .collect(Collectors.toList());

        // Create the ProductModel entity and set the suppliers
        ProductModel product = new ProductModel(
                productDTO.getName(),
                productDTO.getDescription(),
                productDTO.getPrice(),
                productDTO.getTax(),
                productDTO.getTotal(),
                productDTO.getStockQuantity(),
                suppliers);

        // Save the product to the database
        ProductModel savedProduct = productRepository.save(product);
        return productMapper.toDTO(savedProduct);
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        ProductModel existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        if (productDTO.getName() != null) {
            existingProduct.setName(productDTO.getName());
        }

        if (productDTO.getDescription() != null) {
            existingProduct.setDescription(productDTO.getDescription());
        }

        if (productDTO.getPrice() != null) {
            existingProduct.setPrice(productDTO.getPrice());
        }

        if (productDTO.getTax() != null) {
            existingProduct.setTax(productDTO.getTax());
        }

        if (productDTO.getTotal() != null) {
            existingProduct.setTotal(productDTO.getTotal());
        }

        if (productDTO.getStockQuantity() != null) {
            existingProduct.setStockQuantity(productDTO.getStockQuantity());
        }

        // Update properties here
        ProductModel updatedProduct = productRepository.save(existingProduct);
        return productMapper.toDTO(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
}
