package com.peripheral.paymentmanagement.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString @Builder
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private double amount;
    private PaymentType type;
    private PaymentStatus status;
    private String receipt;
    @ManyToOne
    private Student student;
}
