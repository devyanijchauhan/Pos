package org.pgs.postp.controller;

import com.google.zxing.WriterException;
import org.pgs.postp.dto.ProductDTO;
import org.pgs.postp.service.Impl.ProductServiceImpl;
import org.pgs.postp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@PreAuthorize("hasAuthority('ADMIN') || hasAuthority('MANAGER') || hasAuthority('CASHIER')")
@RequestMapping("/api/products")
@CrossOrigin( origins = "http://Localhost:4200")
public class ProductController {

    private final ProductService productService;
    private final ProductServiceImpl productServiceImpl;
    @Autowired
    public ProductController(ProductService productService, ProductServiceImpl productServiceImpl) {
        this.productService = productService;
        this.productServiceImpl = productServiceImpl;
    }


    // Custom response class for success and error cases
    static class Response<T> {
        private final String message;
        private final T data;

        public Response(String message, T data) {
            this.message = message;
            this.data = data;
        }

        public String getMessage() {
            return message;
        }

        public T getData() {
            return data;
        }
    }

    @PostMapping
    public ResponseEntity<Response<ProductDTO>> createProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO createdProduct = productService.createProduct(productDTO);
        return new ResponseEntity<>(new Response<>("Product created successfully", createdProduct), HttpStatus.CREATED);
    }

    @PostMapping("/upload")
    public ResponseEntity<Response<String>> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            productService.processCSV(file);
            return new ResponseEntity<>(new Response<>("CSV processed successfully. Data added successfully.", null), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new Response<>("CSV processing failed: Invalid content", e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (IOException | WriterException e) {
            return new ResponseEntity<>(new Response<>("Failed to process CSV", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("id") Long id) {
        ProductDTO productDTO = productService.getProductById(id);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> productDTOs = productService.getAllProducts();
        return new ResponseEntity<>(productDTOs, HttpStatus.OK);
    }

    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<List<ProductDTO>> getProductsBySupplierId(@PathVariable("supplierId") Long supplierId) {
        List<ProductDTO> products = productService.getProductsBySupplierId(supplierId);
        if (!products.isEmpty()) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/barcode/{barcodeNumber}")
    public ResponseEntity<List<ProductDTO>> getProductsByBarcodeNumber(@PathVariable("barcodeNumber") String barcodeNumber) {
        List<ProductDTO> products = productService.getProductsByBarcodeNumber(barcodeNumber);
        if (!products.isEmpty()) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable("id") Long id, @RequestBody ProductDTO productDTO) {
        productDTO.setProductId(id);
        ProductDTO updatedProduct = productService.updateProduct(id, productDTO);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
