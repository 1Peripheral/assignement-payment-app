package com.peripheral.paymentmanagement.services;

import com.peripheral.paymentmanagement.entities.Payment;
import com.peripheral.paymentmanagement.entities.PaymentStatus;
import com.peripheral.paymentmanagement.entities.PaymentType;
import com.peripheral.paymentmanagement.entities.Student;
import com.peripheral.paymentmanagement.repositories.PaymentRepository;
import com.peripheral.paymentmanagement.repositories.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

@Service @Transactional
public class PaymentService {
    private StudentRepository studentRepo;
    private PaymentRepository paymentRepo;

    public PaymentService(StudentRepository studentRepo, PaymentRepository paymentRepo) {
        this.studentRepo = studentRepo;
        this.paymentRepo = paymentRepo;
    }

    public Payment savePayment(MultipartFile file,
                               LocalDate date,
                               double amount,
                               PaymentType type,
                               String studentCode) throws Exception{
        Path folderPath = Paths.get(System.getProperty("user.home"), "Scratch", "payments");
        if (!Files.exists(folderPath)) {
            Files.createDirectories(folderPath);
        }
        String fileName = UUID.randomUUID().toString();
        Path filePath = Paths.get(System.getProperty("user.home"), "Scratch", "payments",
                fileName + ".pdf");
        Files.copy(file.getInputStream(), filePath);
        Student student = studentRepo.findByCode(studentCode);
        Payment payment = Payment.builder()
                .date(date)
                .type(type)
                .student(student)
                .amount(amount)
                .status(PaymentStatus.CREATED)
                .receipt(filePath.toUri().toString())
                .build();
        return paymentRepo.save(payment);
    }

    public Payment updatePaymentStatus(Long id, PaymentStatus status) {
        Payment payment = paymentRepo.findById(id).orElse(null);
        payment.setStatus(status);
        return paymentRepo.save(payment);
    }

    public byte[] getPaymentFile(Long paymentID) throws Exception {
        Payment payment = paymentRepo.findById(paymentID).orElse(null);
        return Files.readAllBytes(Path.of(URI.create(payment.getReceipt())));
    }
}
