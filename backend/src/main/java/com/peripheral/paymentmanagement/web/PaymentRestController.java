package com.peripheral.paymentmanagement.web;

import com.peripheral.paymentmanagement.entities.Payment;
import com.peripheral.paymentmanagement.entities.PaymentStatus;
import com.peripheral.paymentmanagement.entities.PaymentType;
import com.peripheral.paymentmanagement.entities.Student;
import com.peripheral.paymentmanagement.repositories.PaymentRepository;
import com.peripheral.paymentmanagement.repositories.StudentRepository;
import com.peripheral.paymentmanagement.services.PaymentService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("*")
public class PaymentRestController {
    private StudentRepository studentRepo;
    private PaymentRepository paymentRepo;
    private PaymentService paymentService;

    public PaymentRestController(StudentRepository studentRepository,
                                 PaymentRepository paymentRepository, PaymentService paymentService) {
        this.studentRepo = studentRepository;
        this.paymentRepo = paymentRepository;
        this.paymentService = paymentService;
    }

    @GetMapping(path = "/payments")
    public List<Payment> allPayments() {
        return paymentRepo.findAll();
    }

    @GetMapping(path = "/students/{code}/payments")
    public List<Payment> paymentsByStudent(@PathVariable String code) {
        return paymentRepo.findByStudentCode(code);
    }

    @GetMapping(path = "/payments/{id}")
    public Payment getPaymentById(@PathVariable Long id) {
        return paymentRepo.findById(id).orElse(null);
    }

    @GetMapping(path = "/payments/type")
    public List<Payment> getPaymentById(@RequestParam PaymentType type) {
        return paymentRepo.findByType(type);
    }

    @GetMapping(path = "/payments/status")
    public List<Payment> paymentsByStatus(@RequestParam PaymentStatus status) {
        return paymentRepo.findByStatus(status);
    }

    @GetMapping(path = "/students")
    public List<Student> allStudents() {
        return studentRepo.findAll();
    }

    @GetMapping(path = "/students/{code}")
    public Student getStudentByCode(@PathVariable String code) {
        return studentRepo.findByCode(code);
    }

    @GetMapping(path = "/studentsByProgramID/")
    public List<Student> getStudentsByProgramID(@RequestParam String programID) {
        return studentRepo.findByProgramID(programID);
    }

    @PutMapping("/payments/{id}")
    public Payment updatePaymentStatus(@PathVariable Long id, @RequestParam PaymentStatus status) {
        return this.paymentService.updatePaymentStatus(id, status);
    }

    @PostMapping(path = "/payments", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Payment savePayment(@RequestParam MultipartFile file,
                               LocalDate date,
                               double amount,
                               PaymentType type,
                               String studentCode) throws Exception{
        return this.paymentService.savePayment(
                file, date, amount,
                type, studentCode
        );
    }

    @GetMapping(value = "paymentFile/{paymentID}", produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] getPaymentFile(@PathVariable Long paymentID) throws Exception {
        return this.paymentService.getPaymentFile(paymentID);
    }
}
