package com.deg.country;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@FeignClient(name = "countryService", url = "${external.api.url}")
public interface CountryApiClient {

     Logger log = LoggerFactory.getLogger(CountryApiClient.class);

    /**
     This method retrieves information about a country by its ID using the CircuitBreaker annotation from the Resilience4j library.
     Circuit breaking is a design pattern utilized to manage potential failures by temporarily halting the execution of an operation if a certain threshold of failures is reached.
     When the operation fails, the circuit breaker transitions to an open state, and subsequent calls to the failing operation are intercepted and handled by the specified fallback method.
     The getCountryByIdFallback method is invoked either when the circuit breaker is open or when the operation fails, providing a fallback response to the client.
     @param id The ID of the country to retrieve
     @return ResponseEntity containing the country information
     */
    @GetMapping("/{id}")
    @CircuitBreaker(name = "getCountryByIdService", fallbackMethod = "getCountryByIdFallback")
    ResponseEntity<Country> getCountryById(@PathVariable  Long id);

    default ResponseEntity<Country> getCountryByIdFallback(Long id, Exception e) {

        return ResponseEntity.ok(Country.builder().
                countryName("fallback").
                id(0L).build());
    }


        @PostMapping
        ResponseEntity<Country> createCountry(@RequestBody Country country);






}
