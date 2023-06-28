package com.bank.repository;

import com.bank.model.entity.Transaction;
import io.reactivex.rxjava3.core.Single;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

public interface TransactionRepository extends ReactiveMongoRepository<Transaction, String> {
    /**
     * Metodo que busca las transaciones o movimientos que ha realizado
     * un cliente desde un intervalo de fechas
     *
     * @param firsDate  Fecha Inicio
     * @param lastDate  Fecha Fin
     * @param idProduct Id Producto
     * @return
     */
    @Query("{ 'feTransaction': { $gte: ?0, $lte: ?1 }, 'idProduct': ?2  }")
    Flux<Transaction> findByFeTransaction(LocalDate firsDate, LocalDate lastDate, String idProduct);

    Flux<Transaction> findAllByIdProduct(String idProduct);

    @Query(value = "{ 'idProduct':  ?0, 'commission' :  0.0 }", count = true)
    Single<Long> countTransactionByIdProduct(String idProduct);
}
