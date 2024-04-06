package org.pgs.postp.repository;

import org.pgs.postp.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long> {


    @Query("SELECT p FROM ProductModel p JOIN p.suppliers s WHERE s.supplierID = :supplierId")
    List<ProductModel> findBySupplierId(Long supplierId);

    Optional<ProductModel> findByBarcodeNumber(String barcodeNumber);

}