package org.pgs.postp.repository;

import org.pgs.postp.model.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerModel, Long> {

    boolean existsByEmail(String email);

    boolean existsByPhone(BigInteger phone);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByPhoneAndIdNot(BigInteger phone, Long id);

}
