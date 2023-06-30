package com.bank.service.impl;

import com.bank.exception.AttributeException;
import com.bank.exception.ResourceNotFoundException;
import com.bank.model.entity.Customer;
import com.bank.model.request.CustomerSaveRequest;
import com.bank.model.request.CustomerUpdateRequest;
import com.bank.repository.CustomerRepository;
import com.bank.service.CustomerService;
import com.bank.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Mono<Customer> save(CustomerSaveRequest request) {
        return customerRepository.findByNumDocument(request.getNumDocument())
                .hasElement()
                .flatMap(existsCustomer -> existsCustomer ? Mono.error(new AttributeException("Customer exists"))
                        : customerRepository.save(CustomerMapper.mapRequestToEntity(request)));
    }

    @Override
    public Mono<Customer> update(CustomerUpdateRequest request, String idCustomer) {
        return customerRepository.findById(idCustomer)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Customer not found")))
                .flatMap(customer -> {
                    customer.setName(request.getName());
                    customer.setNumDocument(request.getNumDocument());
                    return customerRepository.save(customer);
                });
    }

    @Override
    public Mono<Customer> findById(String id) {
        return customerRepository.findById(id)
                .switchIfEmpty(Mono.empty());
    }

    @Override
    public Flux<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }


}
