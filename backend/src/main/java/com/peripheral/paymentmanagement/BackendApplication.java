package com.peripheral.paymentmanagement;

import com.peripheral.paymentmanagement.entities.Payment;
import com.peripheral.paymentmanagement.entities.PaymentStatus;
import com.peripheral.paymentmanagement.entities.PaymentType;
import com.peripheral.paymentmanagement.entities.Student;
import com.peripheral.paymentmanagement.repositories.PaymentRepository;
import com.peripheral.paymentmanagement.repositories.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(StudentRepository studentRepo,
										PaymentRepository paymentRepository) {
		return args -> {
			studentRepo.save(Student.builder().id(UUID.randomUUID().toString())
					.firstName("Mohamed")
					.code("1234")
					.programID("SDIA")
					.build());
			studentRepo.save(Student.builder().id(UUID.randomUUID().toString())
					.firstName("Imane")
					.code("1334")
					.programID("SDIA")
					.build());
			studentRepo.save(Student.builder().id(UUID.randomUUID().toString())
					.firstName("Amine")
					.code("12244")
					.programID("GLSID")
					.build());
			studentRepo.save(Student.builder().id(UUID.randomUUID().toString())
					.firstName("Najat")
					.code("1113499")
					.programID("BDCC")
					.build());

			Random rng = new Random();
			PaymentType[] paymentTypes = PaymentType.values();
			studentRepo.findAll().forEach(s -> {
				int nPayment = rng.nextInt(5, 20);
				int tmp = rng.nextInt(paymentTypes.length);
				for (int i = 0 ; i < nPayment ; i++) {
					Payment payment = Payment.builder()
							.amount(rng.nextDouble(1_000, 20_000))
							.type(paymentTypes[tmp])
							.status(PaymentStatus.CREATED)
							.date(LocalDate.now())
							.student(s)
							.build();
					paymentRepository.save(payment);
				}
			});
		};
	}

}
