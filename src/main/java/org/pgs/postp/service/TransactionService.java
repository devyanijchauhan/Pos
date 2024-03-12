package org.pgs.postp.service;

import org.pgs.postp.dto.TransactionDTO;

import java.util.List;

public interface TransactionService {
    List<TransactionDTO> getAllTransactions();

    TransactionDTO getTransactionById(Long id);

    TransactionDTO createTransaction(TransactionDTO transactionDTO);

    TransactionDTO updateTransaction(Long id, TransactionDTO transactionDTO);

    void deleteTransaction(Long id);
}
