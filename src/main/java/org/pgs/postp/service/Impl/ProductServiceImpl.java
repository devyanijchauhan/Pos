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
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper,SupplierRepository supplierRepository) {
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
        this.productMapper = productMapper;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<ProductModel> products = productRepository.findAll();
        return products.stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductById(Long id) {
        ProductModel product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        return productMapper.toDTO(product);
    }

//    @Override
//    public ProductDTO createProduct(ProductDTO productDTO) {
//        if (productDTO.getSupplierID() == null) {
//            throw new IllegalArgumentException("Supplier must be provided");
//        }
//        ProductModel product = productMapper.toEntity(productDTO);
//        ProductModel savedProduct = productRepository.save(product);
//        return productMapper.toDTO(savedProduct);
//    }
@Override
public ProductDTO createProduct(ProductDTO productDTO) {
    if (productDTO.getSupplierID() == null) {
        throw new IllegalArgumentException("Supplier must be provided");
    }

    // Fetch the supplier from the database
    SupplierModel supplier = supplierRepository.findById(productDTO.getSupplierID())
            .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + productDTO.getSupplierID()));

    // Create the ProductModel entity and set the supplier
    ProductModel product = new ProductModel(
            productDTO.getName(),
            productDTO.getDescription(),
            productDTO.getPrice(),
            productDTO.getStockQuantity(),
            supplier);

    // Save the product to the database
    ProductModel savedProduct = productRepository.save(product);
    return productMapper.toDTO(savedProduct);
}

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        ProductModel existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        if(productDTO.getName()!=null){
            existingProduct.setName(productDTO.getName());
        }

        if(productDTO.getDescription()!=null){
            existingProduct.setDescription(productDTO.getDescription());
        }

        if(productDTO.getStockQuantity()!=null){
            existingProduct.setStockQuantity(productDTO.getStockQuantity());
        }

        if(productDTO.getPrice()!=null){
            existingProduct.setPrice(productDTO.getPrice());
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
