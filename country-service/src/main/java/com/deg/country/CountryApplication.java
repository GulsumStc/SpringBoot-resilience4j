package com.deg.country;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static com.deg.country.CountyrController.getBulkhead;

@SpringBootApplication
@EnableFeignClients
public class CountryApplication {

    public static void main(String[] args) {

        SpringApplication.run(CountryApplication.class, args);
    }



}
