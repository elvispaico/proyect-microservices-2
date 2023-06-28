package com.bank.repository;

import com.bank.model.entity.Product;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ProductRepository extends ReactiveCrudRepository<Product, String> {

    /**
     * Metodo que busca un producto por ID,
     * usando query basado en mongodb
     * @param idCustomer
     * @return
     */
    @Query("{'idCustomer' :  ?0 }")
    Flux<Product> findByCustomer(String idCustomer);

}
