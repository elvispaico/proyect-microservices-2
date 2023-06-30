package com.bank.service;

import com.bank.model.entity.Customer;
import com.bank.model.request.CustomerSaveRequest;
import com.bank.model.request.CustomerUpdateRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {
    /**
     * Metodo para guardar un cliente
     *
     * @param request Objeto para guardar solo los campos necesarios
     * @return
     */
    Mono<Customer> save(CustomerSaveRequest request);

    /**
     * Metodo para actualizar datos del cliente, solo nombre
     *
     * @param request
     * @return
     */
    Mono<Customer> update(CustomerUpdateRequest request, String idCustomer);

    /**
     * Metodo que busca un cliente por el ID, que representa la
     * clave primaria
     *
     * @param id
     * @return
     */
    Mono<Customer> findById(String id);


    /**
     * Metodo que retorna todos los clientes registrado
     *
     * @return
     */
    Flux<Customer> findAllCustomers();

}
