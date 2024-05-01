package com.deg.country;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/country")
public class CountyrController {

    private static final Logger log = LoggerFactory.getLogger(CountyrController.class);
    private final CountryService countryService;
    private Integer attempts = 0;

    public CountyrController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Country> getCountryById(@PathVariable  Long id) {
        return ResponseEntity.ok(countryService.getCountryById(id));
    }

    @PostMapping()
    public ResponseEntity<Country> createCountry(@RequestBody Country country) {
        return ResponseEntity.ok(countryService.createCountry(country));
    }


    /**
     * This method demonstrates the usage of RateLimiter annotation in order to limit the number of calls to a method in a specified time frame.
     * If the number of calls exceeds the limit, it falls back to the fallbackRateLimiter method.
     * @return
     */

    @GetMapping("/rateLimiter")
    @RateLimiter(name = "rateLimiter" , fallbackMethod = "rateLimiterFallback")
    public ResponseEntity<String> rateLimiter() {
        return ResponseEntity.ok("RateLimiter");
    }

    public ResponseEntity<String> rateLimiterFallback(Exception e) {
        // Implement the fallback logic here
        // This method is invoked when the rate limit is exceeded
        // You can customize the response message and status code according to your requirements
        return new ResponseEntity<>("RateLimiter Fallback", HttpStatus.TOO_MANY_REQUESTS);
    }



    /*
        @TimeLimiter is a Resilience4J annotation used to specify that a method must complete within a certain time frame
        when called. It wraps the method with a special class that limits the execution time usin
        doesn't complete within the specified time frame, a designated fallback method is invoked.
     */

    /**
     * This method demonstrates the usage of TimeLimiter annotation in order to limit the execution time of a CompletableFuture task.
     * It returns a CompletableFuture<String> that simulates a task running for 6 seconds.
     * If the task exceeds the time limit, it falls back to the fallbackTimeLimiter method.
     * @return CompletableFuture<String> representing the result of the time-limited task
     */

    @GetMapping("/timeLimiter")
    @TimeLimiter(name = "getCountryTimeLimiter", fallbackMethod = "fallbackTimeLimiter")
    public CompletableFuture<String> getCountryTimeLimiter() {
        return CompletableFuture.supplyAsync(() -> {
            for (int i = 1; i <= 6; i++) {
                System.out.println("Elapsed time: " + i + " seconds");
                try {
                    Thread.sleep(1000); // Wait for 1 second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "TimeLimiter";
        });
    }

    public CompletableFuture<String> fallbackTimeLimiter(Exception e) {
        return CompletableFuture.completedFuture("TimeLimiter Fallback");
    }



    /**

     This method demonstrates the usage of Bulkhead annotation to limit concurrent access to a specific operation
     so that it does not overload the service and protect other services from being affected.
     It returns a String representing the result of the operation, indicating successful completion.
     If the bulkhead limit is reached, it falls back to the fallbackBulkhead method.
     @return String representing the result of the operation
     */

    @GetMapping("/bulkhead")
    @Bulkhead(name = "getCountryBulkhead", fallbackMethod = "fallbackBulkhead", type = Bulkhead.Type.THREADPOOL)
    public  static  String getBulkhead() {
        System.out.println( "Operation completed by [" + Thread.currentThread().getName() + "]");
        return "Bulkhead";
    }

    public String fallbackBulkhead(Exception e) {
        return "Country service is full, no more operations are allowed." + HttpStatus.TOO_MANY_REQUESTS;
    }
}
