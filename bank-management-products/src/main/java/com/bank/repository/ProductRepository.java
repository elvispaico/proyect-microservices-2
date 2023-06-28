package com.bank.repository;

import com.bank.model.entity.Product;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ProductRepository extends ReactiveCrudRepository<Product, String> {

    /**
     * Metodo que busca un producto por ID,
     * usando query basado en mongodb
     *
     * @param idCustomer
     * @return
     */
    @Query("{'idCustomer' :  ?0 }")
    Flux<Product> findByCustomer(String idCustomer);

    Flowable<Product> findAllByIdCustomer(String idCustomer);

    @ExistsQuery("{ 'idCustomer': ?0  ,'codTypeService': '06' }")
    Single<Boolean> existsProductCard(String idCustomer);
}
