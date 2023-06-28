package com.bank.service.impl;

import com.bank.enums.TypeCustomer;
import com.bank.enums.TypeService;
import com.bank.exception.AttributeException;
import com.bank.exception.ResourceNotFoundException;
import com.bank.model.entity.Customer;
import com.bank.model.entity.Product;
import com.bank.repository.CustomerRepository;
import com.bank.repository.ProductRepository;
import com.bank.service.ProductService;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    @Override
    public Single<Product> save(Product request) {
        var response = Maybe.fromPublisher(customerRepository.findById(request.getIdCustomer()))
                .switchIfEmpty(Single.error(new ResourceNotFoundException("Customer not found")))
                .flatMap(customer -> {
                    if (isCustomerPersonal(customer.getCodTypeCustomer())) {
                        return saveProductCustomerPersonal(customer, request);
                    } else {
                        return saveProductCustomerBussines(request);
                    }
                });

        return response;
    }

    @Override
    public Single<Product> findById(String id) {
        return Maybe.fromPublisher(productRepository.findById(id))
                .switchIfEmpty(Single.error(new ResourceNotFoundException("product not found")));
    }

    private Single<Product> saveProductCustomerBussines(Product request) throws AttributeException {
        if (isVerifyCustomerBussinessAccountSuccess(request) && isVerifyCommissionSuccess(request)) {
            return Single.fromPublisher(productRepository.save(request));
        } else {
            return Single.error(new AttributeException("Operacion no disponible"));
        }
    }

    private Single<Product> saveProductCustomerPersonal(Customer customer, Product request) {
        var response = Observable.fromPublisher(productRepository.findByCustomer(request.getIdCustomer()))
                .filter(product -> product.getCodTypeService().equals(request.getCodTypeService()))
                .isEmpty()
                .flatMap(isValue -> {
                    if (isValue) {
                        if (isVerifyCommissionSuccess(request)) {
                            return Single.fromPublisher(productRepository.save(request));
                        } else {
                            return Single.error(new AttributeException("Invalid commission"));
                        }
                    } else {
                        return Single.error(new AttributeException("Client already has registered accounts"));
                    }

                });
        return response;
    }

    private boolean isVerifyCommissionSuccess(Product request) {

        if (isTypeServiceWhitCeroCommision(request.getCodTypeService())) {
            return request.getCommission() == 0.0;
        } else if (request.getCodTypeService().equals(TypeService.CURRENT.getValue())) {
            return request.getCommission() > 0.0;
        }

        return true;
    }

    /**
     * Metodo que verifica si un cliente es personal
     *
     * @param codTypeCustomer parametro tipo String
     * @return valor devuelto tipo Boolean
     */
    private boolean isCustomerPersonal(String codTypeCustomer) {
        return codTypeCustomer.equals(TypeCustomer.PERSONAL.getValue());
    }

    /**
     * Metodo que evalua los tipos de cuenta que no tienen comision
     * por mantenimiento
     *
     * @param codTypeService parametro tipo string
     * @return parametro tipo boolean
     */
    private boolean isTypeServiceWhitCeroCommision(String codTypeService) {
        return codTypeService.equals(TypeService.SAVING.getValue())
                || codTypeService.equals(TypeService.FIXEDTERM.getValue());
    }

    /**
     * Metodo que verifica que un cliente empresarial solo puede guardar cuentas corrientes
     *
     * @param request
     * @return
     */
    private boolean isVerifyCustomerBussinessAccountSuccess(Product request) {
        return request.getCodTypeService().equals(TypeService.CURRENT.getValue());
    }
}
