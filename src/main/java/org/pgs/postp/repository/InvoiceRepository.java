package org.pgs.postp.repository;

import org.pgs.postp.model.InvoiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceModel, Long> {


    @Query("SELECT SUM(i.totalMRP) FROM InvoiceModel i")
    Long getTotalMRP();

    @Query("SELECT SUM(i.totalTax) FROM InvoiceModel i")
    Long getTotalTax();

    @Query("SELECT SUM(i.totalDiscount) FROM InvoiceModel i")
    Long getTotalDiscount();

    @Query("SELECT SUM(i.totalPrice) FROM InvoiceModel i")
    Long getTotalPrice();

    //for week
    @Query("SELECT COUNT(i) FROM InvoiceModel i WHERE i.dateTime BETWEEN :startDate AND :endDate")
    int countByCreatedAtBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT COALESCE(SUM(i.totalMRP), 0) FROM InvoiceModel i WHERE i.dateTime BETWEEN :startDate AND :endDate")
    Long getTotalMRPForCurrentWeek(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT COALESCE(SUM(i.totalTax), 0) FROM InvoiceModel i WHERE i.dateTime BETWEEN :startDate AND :endDate")
    Long getTotalTaxForCurrentWeek(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT COALESCE(SUM(i.totalDiscount), 0) FROM InvoiceModel i WHERE i.dateTime BETWEEN :startDate AND :endDate")
    Long getTotalDiscountForCurrentWeek(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT COALESCE(SUM(i.totalPrice), 0) FROM InvoiceModel i WHERE i.dateTime BETWEEN :startDate AND :endDate")
    Long getTotalPriceForCurrentWeek(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    //for Month
    @Query("SELECT COALESCE(SUM(i.totalMRP), 0) FROM InvoiceModel i WHERE i.dateTime BETWEEN :startDate AND :endDate")
    Long getTotalMRPForCurrentMonth(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT COALESCE(SUM(i.totalTax), 0) FROM InvoiceModel i WHERE i.dateTime BETWEEN :startDate AND :endDate")
    Long getTotalTaxForCurrentMonth(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT COALESCE(SUM(i.totalDiscount), 0) FROM InvoiceModel i WHERE i.dateTime BETWEEN :startDate AND :endDate")
    Long getTotalDiscountForCurrentMonth(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT COALESCE(SUM(i.totalPrice), 0) FROM InvoiceModel i WHERE i.dateTime BETWEEN :startDate AND :endDate")
    Long getTotalPriceForCurrentMonth(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

}
