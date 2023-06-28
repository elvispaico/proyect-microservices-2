package com.bank.controller;

import com.bank.model.entity.Customer;
import com.bank.model.request.CustomerSaveRequest;
import com.bank.model.request.CustomerUpdateRequest;
import com.bank.model.CustomerProductResponse;
import com.bank.model.MessageResponse;
import com.bank.service.CustomerService;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/bank/customers")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public Maybe<ResponseEntity<MessageResponse>> save(@RequestBody CustomerSaveRequest request) {
        return customerService.save(request)
                .map(value -> new ResponseEntity<>(
                        new MessageResponse(HttpStatus.CREATED.value(), "Customer save success"),
                        HttpStatus.CREATED)
                );
    }

    @PutMapping(value = "/{id}")
    public Maybe<ResponseEntity<MessageResponse>> update(@PathVariable String id, @RequestBody CustomerUpdateRequest request) {

        return customerService.update(request, id)
                .map(value -> new ResponseEntity<>(
                        new MessageResponse(HttpStatus.CREATED.value(), "Customer save success"),
                        HttpStatus.OK)
                );
    }

    @GetMapping(value = "/{id}")
    public Maybe<Customer> findById(@PathVariable String id) {
        return customerService.findById(id);
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Observable<Customer> findAll() {
        return customerService.findAllCustomers();
    }


    @GetMapping(value = "/{idCustomer}/products")
    public Single<CustomerProductResponse> findCustomerWithProducts(@PathVariable String idCustomer) {
        return customerService.findCustomerWhitProducts(idCustomer);
    }
}
