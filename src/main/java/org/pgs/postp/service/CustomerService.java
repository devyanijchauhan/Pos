package org.pgs.postp.service;

import com.google.zxing.WriterException;
import org.pgs.postp.dto.CustomerDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CustomerService {
    List<CustomerDTO> getAllCustomers();

    CustomerDTO getCustomerById(Long id);

    CustomerDTO createCustomer(CustomerDTO customerDTO) throws RuntimeException;

    CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) throws RuntimeException;

    void deleteCustomer(Long id);

    void processCSV(MultipartFile file) throws IOException, RuntimeException, WriterException;


}
