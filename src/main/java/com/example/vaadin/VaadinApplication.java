package com.example.vaadin;

import com.example.vaadin.domain.customer.model.Customer;
import com.example.vaadin.domain.customer.repo.CustomerRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class VaadinApplication {

    public static void main(String[] args) {
        SpringApplication.run(VaadinApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(CustomerRepo repo) {
        return (args) -> {
            // 임의로 생성할 엔티티
            Customer saved = repo.save(new Customer("Jack", "Bauer"));
            repo.save(new Customer("Chloe", "O'Brian"));
            repo.save(new Customer("Alksjf", "Bauer"));
            repo.save(new Customer("David", "Palmer"));
            repo.save(new Customer("Michelle", "Dessler"));

            //데이터 확인
            log.info("Customers found with findAll() : ");
            log.info("---------------------------------");
            for (Customer customer : repo.findAll()) {
                log.info("{}", customer);
            }
            log.info("---------------------------------");

            // fetch an individual customer by ID
            Customer customer = repo.findById(saved.getUuid()).get();
            log.info("Customer found with findOne({}) : ", saved.getUuid());
            log.info("--------------------------------");
            log.info("{}", customer);
            log.info("--------------------------------");

            // fetch customers by last name
            log.info("Customer found with findByLastNameStartsWithIgnoreCase('Bauer'):");
            log.info("--------------------------------------------");
            for (Customer bauer : repo
                .findByLastNameStartsWithIgnoreCase("Bauer")) {
                log.info("{}", bauer);
            }
            log.info("--------------------------------------------");
        };
    }

}
