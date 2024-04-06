package org.pgs.postp.repository;

import org.pgs.postp.model.VoucherModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherRepository extends JpaRepository<VoucherModel, Long> {

    VoucherModel findByVoucherCode(String voucherCode);


}
