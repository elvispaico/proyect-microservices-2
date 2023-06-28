package com.bank.repository;

import com.bank.model.entity.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerRepository extends ReactiveCrudRepository<Customer, String> {

    /**
     * Metodo para buscar un cliente por el numero de documento
     *
     * @param numDocument
     * @return
     */
    Mono<Customer> findByNumDocument(String numDocument);

}
