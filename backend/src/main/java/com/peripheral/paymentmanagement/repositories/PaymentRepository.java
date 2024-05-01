package com.peripheral.paymentmanagement.repositories;

import com.peripheral.paymentmanagement.entities.Payment;
import com.peripheral.paymentmanagement.entities.PaymentStatus;
import com.peripheral.paymentmanagement.entities.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByStudentCode(String code);
    List<Payment> findByStatus(PaymentStatus status);
    List<Payment> findByType(PaymentType type);
}
