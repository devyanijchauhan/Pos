package org.pgs.postp.service.Impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.pgs.postp.dto.ProductDTO;
import org.pgs.postp.mapper.ProductMapper;
import org.pgs.postp.model.InvoiceModel;
import org.pgs.postp.model.ProductModel;
import org.pgs.postp.model.SupplierModel;
import org.pgs.postp.repository.ProductRepository;
import org.pgs.postp.repository.SupplierRepository;
import org.pgs.postp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.*;
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
    public ProductDTO getProductByBarcodeNumber(String barcodeNumber) {
        ProductModel product = productRepository.findByBarcodeNumber(barcodeNumber)
                .orElseThrow(() -> new RuntimeException("Product not found with barcode number: " + barcodeNumber));
        return productMapper.toDTO(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> getProductsBySupplierId(Long supplierId) {
        // Fetch the products by supplier ID using the repository method
        List<ProductModel> products = productRepository.findBySupplierId(supplierId);

        // Map the found product entities to DTOs
        return products.stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> getProductsByBarcodeNumber(String barcodeNumber) {
        Optional<ProductModel> products = productRepository.findByBarcodeNumber(barcodeNumber);
        return products.stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
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

        // Generate unique barcode number
        String barcodeNumber = generateUniqueBarcodeNumber();

        // Generate barcode image
        byte[] barcodeImageBytes;
        try {
            barcodeImageBytes = generateBarcodeImage(barcodeNumber, 200, 50);
        } catch (WriterException | IOException e) {
            throw new RuntimeException("Error generating barcode image", e);
        }


        // Create the ProductModel entity and set the suppliers
        ProductModel product = new ProductModel(
                productDTO.getName(),
                productDTO.getDescription(),
                productDTO.getPrice(),
                productDTO.getTax(),
                productDTO.getTotal(),
                productDTO.getStockQuantity(),
                productDTO.getPurchasePrice(),
                barcodeNumber,
                barcodeImageBytes, // Updated to use the generated barcode image byte array
                suppliers);
        calculatePrice(product);
        // Save the product to the database
        ProductModel savedProduct = productRepository.save(product);
        return productMapper.toDTO(savedProduct);
    }
    // Helper method to generate a unique barcode number
    private String generateUniqueBarcodeNumber() {
        String barcodeNumber;
        boolean unique = false;
        do {

            barcodeNumber = generateBarcodeNumber(); // Generate a random barcode number
            // Check if the generated barcode number already exists in the database
            if (!productRepository.findByBarcodeNumber(barcodeNumber).isPresent()) {
                unique = true; // Set unique to true if the barcode number is not found in the database
            }
        } while (!unique);

        return barcodeNumber;
    }


    private byte[] generateBarcodeImage(String barcodeText, int width, int height) throws WriterException, IOException {
        // Set barcode parameters
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.MARGIN, 0); // Set margin to 0
        BitMatrix bitMatrix = new MultiFormatWriter().encode(barcodeText, BarcodeFormat.CODE_128, width, height, hints);

        // Convert bitMatrix to byte array
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
        return outputStream.toByteArray();
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

        if (productDTO.getPurchasePrice() != null) {
            existingProduct.setPurchasePrice(productDTO.getPurchasePrice());
        }

//        if (productDTO.getBarcodeNumber() != null) {
//            existingProduct.setBarcodeNumber(productDTO.getBarcodeNumber());
//        }
//        if (productDTO.getBarcodeImage() != null) {
//            existingProduct.setBarcodeImage(productDTO.getBarcodeImage());
//        }

        // Calculate total price
        calculatePrice(existingProduct);

        // Update properties here
        ProductModel updatedProduct = productRepository.save(existingProduct);
        return productMapper.toDTO(updatedProduct);
    }

    private void calculatePrice(ProductModel product) {
        BigDecimal price = product.getPrice() != null ? product.getPrice() : BigDecimal.ZERO;
        BigDecimal tax = product.getTax() != null ? product.getTax() : BigDecimal.ZERO;


        // Calculate total price
        BigDecimal total = price.add(tax);
        product.setTotal(total);
    }


    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }


    public void processCSV(MultipartFile file) throws IOException, WriterException {
        // Check CSV file headers
        BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
//        String headerLine = br.readLine();
//        if (headerLine == null || !headerLine.equals("Name,Description,Price,Tax,Total,StockQuantity,PurchasePrice")) {
//            throw new IllegalArgumentException("Invalid CSV file format or headers");
//        }

        // Skip the header line
        br.readLine();


        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            String name = data[0].trim();
            String description = data[1].trim();
            BigDecimal price = new BigDecimal(data[2].trim());
            BigDecimal tax = new BigDecimal(data[3].trim());
            BigDecimal total = new BigDecimal(data[4].trim());
            BigDecimal stockQuantity = new BigDecimal(data[5].trim());
            BigDecimal purchasePrice = new BigDecimal(data[6].trim());

            String barcodeNumber = generateUniqueBarcodeNumber();
            String[] supplierIds = data[7].trim().split("\\s+");
            List<SupplierModel> supplierModels = new ArrayList<>();
            for(var supId: supplierIds) {
//                var suppModel = supplierRepository.findById(Long.parseLong(supId)).orElse(null);
//                if (suppModel != null) {
//                    supplierModels.add(suppModel);
//                }
                try {
                    long supplierId = Long.parseLong(supId.trim());
                    SupplierModel suppModel = supplierRepository.findById(supplierId)
                            .orElse(null);
                    if (suppModel != null) {
                        supplierModels.add(suppModel);
                    } else {
                        // If a supplier with the given ID is not found, throw an exception
                        throw new IllegalArgumentException("Supplier not found with id: " + supplierId);
                    }
                } catch (NumberFormatException e) {
                    // If the supplier ID cannot be parsed as a long value, throw an exception
                    throw new IllegalArgumentException("Invalid supplier ID: " + supId);
                }
            }

            ProductModel product = new ProductModel();
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setTax(tax);
            product.setTotal(total);
            product.setStockQuantity(stockQuantity);
            product.setPurchasePrice(purchasePrice);
            product.setBarcodeNumber(barcodeNumber);
            product.setBarcodeImage(generateBarcode(barcodeNumber, 200, 50));
            product.setSuppliers(supplierModels);
            productRepository.save(product);
        }
        br.close();
    }

    private String generateBarcodeNumber() {
        // Generate random barcode (you can implement your own logic)
        Random random = new Random();
        StringBuilder barcode = new StringBuilder("P");
        for (int i = 0; i < 5; i++) {
            barcode.append(random.nextInt(5));
        }
        return barcode.toString();
    }

    private String generateBarcodeImage() {
        try {
            String barcodeText = generateBarcodeNumber(); // Generate a random barcode number
            byte[] barcodeBytes = generateBarcode(barcodeText, 200, 50); // Generate barcode image bytes
            return Base64.getEncoder().encodeToString(barcodeBytes); // Convert bytes to base64 encoded string
        } catch (Exception e) {
            throw new RuntimeException("Error generating barcode image");
        }
    }

    public static byte[] generateBarcode(String barcodeText, int width, int height) throws WriterException, IOException {
        // Set barcode parameters
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.MARGIN, 0); // Set margin to 0
        BitMatrix bitMatrix = new MultiFormatWriter().encode(barcodeText, BarcodeFormat.CODE_128, width, height, hints);

        // Convert bitMatrix to byte array
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
        return outputStream.toByteArray();
    }

}
