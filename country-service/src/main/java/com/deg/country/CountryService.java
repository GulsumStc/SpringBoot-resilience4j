package com.deg.country;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class CountryService {

    private final CountryApiClient countryApiClient;

    private Integer attempts = 0;

    public CountryService(CountryApiClient countryApiClient) {
        this.countryApiClient = countryApiClient;
    }


    public Country getCountryById(Long id) {
        log.info(" get country by id method is successful");
        return countryApiClient.getCountryById(id).getBody();
    }


    // Not: make url wrong in application.yml to observe retry mechanism
    @Retry(name = "createCountryService", fallbackMethod = "createCountryFallback")
    public Country  createCountry(Country country) {

          log.info("Create Country retry attempt  {}", ++attempts);
        return countryApiClient.createCountry(country).getBody();
    }

    // imp : When using fallback methods, ensure that the return types match exactly.

     public  Country createCountryFallback(Country country, Exception e) {

            log.info("Country service is down now We can not create country, this is fallback response");

            Country fallbackCountry = Country.builder().countryName("FALLBACK").id(0L).build();

            return fallbackCountry;
    }

}
