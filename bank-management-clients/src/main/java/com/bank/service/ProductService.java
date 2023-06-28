package com.bank.service;

import com.bank.model.entity.Product;
import io.reactivex.rxjava3.core.Single;

public interface ProductService {

    /**
     * Metodo que guarda un producto en la base de datos
     * @param request
     * @return
     */
    Single<Product> save(Product request);

    /**
     * Metodo para buscar un producto por ID, que representa
     * la clave primaria
     * @param id clave primaria
     * @return Objeto Single
     */
    Single<Product> findById(String id);
}
