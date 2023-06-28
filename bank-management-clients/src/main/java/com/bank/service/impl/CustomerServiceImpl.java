package com.bank.service.impl;

import com.bank.exception.AttributeException;
import com.bank.exception.ResourceNotFoundException;
import com.bank.model.entity.Customer;
import com.bank.model.request.CustomerSaveRequest;
import com.bank.model.request.CustomerUpdateRequest;
import com.bank.model.CustomerProductResponse;
import com.bank.repository.CustomerRepository;
import com.bank.repository.ProductRepository;
import com.bank.service.CustomerService;
import com.bank.service.mapper.CustomerMapper;
import com.bank.service.mapper.ProductMapper;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    @Override
    public Maybe<Customer> save(CustomerSaveRequest request) {

        return Maybe.fromPublisher(customerRepository.findByNumDocument(request.getNumDocument()))
                .isEmpty()
                .flatMap(isEmpty -> {
                    return isEmpty ? Single.fromPublisher(customerRepository.save(CustomerMapper.mapRequestToEntity(request)))
                            : Single.error(new AttributeException("Customer exists"));
                })
                .toMaybe();
    }

    @Override
    public Maybe<Customer> update(CustomerUpdateRequest request, String idCustomer) {
        return Maybe.fromPublisher(customerRepository.findById(idCustomer))
                .switchIfEmpty(Maybe.error(new ResourceNotFoundException("Customer not found")))
                .flatMap(customer -> {
                    customer.setName(request.getName());
                    customer.setNumDocument(request.getNumDocument());
                    return Maybe.fromPublisher(customerRepository.save(customer));
                });
    }

    @Override
    public Maybe<Customer> findById(String id) {
        return Maybe.fromPublisher(customerRepository.findById(id))
                .switchIfEmpty(Maybe.error(() -> new ResourceNotFoundException("Customer not found")))
                .flatMap(customer -> Maybe.just(customer));
    }

    @Override
    public Observable<Customer> findAllCustomers() {
        return Observable.fromPublisher(customerRepository.findAll());
    }

    @Override
    public Single<CustomerProductResponse> findCustomerWhitProducts(String idCustomer) {
        var response = Single.fromPublisher(customerRepository.findById(idCustomer))
                .flatMap(customer -> {
                    return Observable.fromPublisher(productRepository.findByCustomer(customer.getId()))
                            .toList()
                            .flatMap(lisProducts -> {
                                CustomerProductResponse ctResponse = CustomerProductResponse.builder()
                                        .id(customer.getId())
                                        .name(customer.getName())
                                        .numDocument(customer.getNumDocument())
                                        .codTypeCustomer(customer.getCodTypeCustomer())
                                        .products(ProductMapper.mapListProductToListProductDto(lisProducts))
                                        .build();
                                return Single.just(ctResponse);
                            });
                });
        return response;
    }

}
