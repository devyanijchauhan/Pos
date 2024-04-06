package org.pgs.postp.service.Impl;

import org.pgs.postp.dto.TransactionDetailDTO;
import org.pgs.postp.mapper.TransactionDetailMapper;
import org.pgs.postp.model.*;
import org.pgs.postp.repository.ProductRepository;
import org.pgs.postp.repository.TransactionDetailRepository;
import org.pgs.postp.repository.TransactionRepository;
import org.pgs.postp.service.TransactionDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionDetailServiceImpl implements TransactionDetailService {

    private final TransactionDetailRepository transactionDetailRepository;

    private final TransactionRepository transactionRepository;

    private final ProductRepository productRepository;
    private final TransactionDetailMapper transactionDetailMapper;

    @Autowired
    public TransactionDetailServiceImpl(TransactionDetailRepository transactionDetailRepository, TransactionDetailMapper transactionDetailMapper, TransactionRepository transactionRepository, ProductRepository productRepository) {
        this.transactionDetailRepository = transactionDetailRepository;
        this.transactionRepository = transactionRepository;
        this.productRepository = productRepository;
        this.transactionDetailMapper = transactionDetailMapper;
    }

    @Override
    public List<TransactionDetailDTO> getAllTransactionDetails() {
        List<TransactionDetailModel> transactionDetails = transactionDetailRepository.findAll();
        return transactionDetails.stream()
                .map(transactionDetailMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TransactionDetailDTO getTransactionDetailById(Long id) {
        TransactionDetailModel transactionDetail = transactionDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction Detail not found with id: " + id));
        return transactionDetailMapper.toDTO(transactionDetail);
    }



    @Override
    public TransactionDetailDTO createTransactionDetail(TransactionDetailDTO transactionDetailDTO) {
        if (transactionDetailDTO.getTransactionID() == null) {
            throw new IllegalArgumentException("Transaction ID must be provided");
        }
        if (transactionDetailDTO.getProductID() == null) {
            throw new IllegalArgumentException("Product ID must be provided");
        }

        TransactionModel transaction = transactionRepository.findById(transactionDetailDTO.getTransactionID())
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + transactionDetailDTO.getTransactionID()));
        ProductModel product = productRepository.findById(transactionDetailDTO.getProductID())
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + transactionDetailDTO.getProductID()));


        TransactionDetailModel transactionDetail = new TransactionDetailModel(
                transaction,
                product,
                transactionDetailDTO.getQuantity(),
                transactionDetailDTO.getUnitPrice(),
                transactionDetailDTO.getDiscount() );


        TransactionDetailModel savedTransactionDetail = transactionDetailRepository.save(transactionDetail);
        return transactionDetailMapper.toDTO(savedTransactionDetail);

    }

    @Override
    public TransactionDetailDTO updateTransactionDetail(Long id, TransactionDetailDTO transactionDetailDTO) {
        TransactionDetailModel existingTransactionDetail = transactionDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction Detail not found with id: " + id));

        existingTransactionDetail.setQuantity(transactionDetailDTO.getQuantity());
        existingTransactionDetail.setUnitPrice(transactionDetailDTO.getUnitPrice());
        existingTransactionDetail.setDiscount(transactionDetailDTO.getDiscount());

        TransactionDetailModel updatedTransactionDetail = transactionDetailRepository.save(existingTransactionDetail);

        return transactionDetailMapper.toDTO(updatedTransactionDetail);
    }

    @Override
    public void deleteTransactionDetail(Long id) {
        if (!transactionDetailRepository.existsById(id)) {
            throw new RuntimeException("Transaction Detail not found with id: " + id);
        }
        transactionDetailRepository.deleteById(id);
    }
}
