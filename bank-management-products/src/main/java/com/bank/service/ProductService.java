package com.bank.service;

import com.bank.model.entity.Product;
import com.bank.model.response.ReportProductResponse;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;

public interface ProductService {

    /**
     * Metodo que guarda un producto en la base de datos
     *
     * @param request
     * @return
     */
    Single<Product> save(Product request);

    /**
     * Metodo para buscar un producto por ID, que representa
     * la clave primaria
     *
     * @param id clave primaria
     * @return Objeto Single
     */
    Single<Product> findById(String id);

    Flowable<Product> findAllProductByIdCustomer(String idCustomer);

    Maybe<ReportProductResponse> findProductWhitTransactionCommission(String idProduct);

    Maybe<Product> saveAccountPersonalVip(Product request);
}
