package org.pgs.postp.controller;

import com.google.zxing.WriterException;
import org.pgs.postp.dto.SupplierDTO;
import org.pgs.postp.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@PreAuthorize("hasAuthority('ADMIN') || hasAuthority('MANAGER')")
@RequestMapping("/api/suppliers")
@CrossOrigin( origins = "http://Localhost:4200")
public class SupplierController {

    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

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
    public ResponseEntity<Response<SupplierDTO>> createSupplier(@RequestBody SupplierDTO supplierDTO) {
        SupplierDTO createdSupplier = supplierService.createSupplier(supplierDTO);
        return new ResponseEntity<>(new Response<>("Supplier created successfully", createdSupplier), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierDTO> getSupplierById(@PathVariable("id") Long id) {
        SupplierDTO supplierDTO = supplierService.getSupplierById(id);
        return new ResponseEntity<>(supplierDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<SupplierDTO>> getAllSuppliers() {
        List<SupplierDTO> supplierDTOs = supplierService.getAllSuppliers();
        return new ResponseEntity<>(supplierDTOs, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierDTO> updateSupplier(@PathVariable("id") Long id, @RequestBody SupplierDTO supplierDTO) {
        supplierDTO.setSupplierID(id);
        SupplierDTO updatedSupplier = supplierService.updateSupplier(id, supplierDTO);
        return new ResponseEntity<>(updatedSupplier, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable("id") Long id) {
        supplierService.deleteSupplier(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/upload")
    public ResponseEntity<Response<String>> uploadCSVFile(@RequestParam("file") MultipartFile file) {
        try {
            supplierService.processCSV(file);
            return new ResponseEntity<>(new Response<>("CSV processed successfully. Data added successfully.", null), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new Response<>("CSV processing failed: Invalid content", e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            return new ResponseEntity<>(new Response<>("Failed to process CSV", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
