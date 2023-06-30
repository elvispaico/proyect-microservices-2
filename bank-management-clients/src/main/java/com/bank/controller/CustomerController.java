package com.bank.controller;

import com.bank.model.MessageResponse;
import com.bank.model.entity.Customer;
import com.bank.model.request.CustomerSaveRequest;
import com.bank.model.request.CustomerUpdateRequest;
import com.bank.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/bank/customers")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public Mono<ResponseEntity<MessageResponse>> save(@RequestBody CustomerSaveRequest request) {
        return customerService.save(request)
                .map(value -> new ResponseEntity<>(
                        new MessageResponse(HttpStatus.CREATED.value(), "Customer save success"),
                        HttpStatus.CREATED)
                );
    }

    @PutMapping(value = "/{id}")
    public Mono<ResponseEntity<MessageResponse>> update(@PathVariable String id, @RequestBody CustomerUpdateRequest request) {
        return customerService.update(request, id)
                .map(value -> new ResponseEntity<>(
                        new MessageResponse(HttpStatus.CREATED.value(), "Customer save success"),
                        HttpStatus.OK)
                );
    }

    @GetMapping(value = "/{id}")
    public Mono<Customer> findById(@PathVariable String id) {
        return customerService.findById(id);
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Customer> findAll() {
        return customerService.findAllCustomers();
    }


}
