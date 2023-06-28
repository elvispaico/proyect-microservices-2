package com.bank.repository;

import com.bank.model.entity.Transaction;
import io.reactivex.rxjava3.core.Flowable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TransactionRepository extends ReactiveMongoRepository<Transaction,String> {

    Flowable<Transaction> findByIdProduct(String idProduct);
}
