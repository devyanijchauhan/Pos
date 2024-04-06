package org.pgs.postp.repository;

import org.pgs.postp.model.TransactionDetailModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDetailRepository extends JpaRepository<TransactionDetailModel, Long> {

}

