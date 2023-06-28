package com.bank.service;

import com.bank.model.entity.Customer;
import com.bank.model.request.CustomerSaveRequest;
import com.bank.model.request.CustomerUpdateRequest;
import com.bank.model.CustomerProductResponse;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface CustomerService {
    /**
     * Metodo para guardar un cliente
     *
     * @param request Objeto para guardar solo los campos necesarios
     * @return
     */
    Maybe<Customer> save(CustomerSaveRequest request);

    /**
     * Metodo para actualizar datos del cliente, solo nombre
     *
     * @param request
     * @return
     */
    Maybe<Customer> update(CustomerUpdateRequest request, String idCustomer);

    /**
     * Metodo que busca un cliente por el ID, que representa la
     * clave primaria
     *
     * @param id
     * @return
     */
    Maybe<Customer> findById(String id);


    /**
     * Metodo que retorna todos los clientes registrado
     *
     * @return
     */
    Observable<Customer> findAllCustomers();

    /**
     * Metodos que busca un cliente porel ID, en la consulta
     * tambien incluye un lista de todos los productos que tiene
     * el cliente
     *
     * @param idCustomer
     * @return
     */
    Single<CustomerProductResponse> findCustomerWhitProducts(String idCustomer);

}
