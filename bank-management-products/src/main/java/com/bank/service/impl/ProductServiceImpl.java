package com.bank.service.impl;

import com.bank.api.CustomerApi;
import com.bank.enums.TypeCustomer;
import com.bank.enums.TypeService;
import com.bank.exception.AttributeException;
import com.bank.exception.ResourceNotFoundException;
import com.bank.model.entity.Product;
import com.bank.model.entity.Transaction;
import com.bank.model.response.CustomerResponse;
import com.bank.model.response.ReportProductResponse;
import com.bank.model.response.TransactionResponse;
import com.bank.repository.ProductRepository;
import com.bank.repository.TransactionRepository;
import com.bank.service.ProductService;
import com.bank.util.BussinessLogic;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final TransactionRepository transactionRepository;
    private final CustomerApi customerApi;

    @Override
    public Single<Product> save(Product request) {
        return Maybe.fromPublisher(customerApi.findCustomerById(request.getIdCustomer()))
                .switchIfEmpty(Single.error(new ResourceNotFoundException("Customer not found")))
                .flatMap(customer -> {
                    if (isCustomerPersonal(customer.getCodTypeCustomer())) {
                        return saveProductCustomerPersonal(customer, request);
                    } else {
                        return saveProductCustomerBussines(request, customer);
                    }
                });
    }

    @Override
    public Single<Product> findById(String id) {
        return Maybe.fromPublisher(productRepository.findById(id))
                .switchIfEmpty(Single.error(new ResourceNotFoundException("product not found")));
    }

    @Override
    public Flowable<Product> findAllProductByIdCustomer(String idCustomer) {
        return productRepository.findAllByIdCustomer(idCustomer);
    }

    @Override
    public Maybe<ReportProductResponse> findProductWhitTransactionCommission(String idProduct) {
        var response = Maybe.fromPublisher(productRepository.findById(idProduct))
                .switchIfEmpty(Maybe.error(new ResourceNotFoundException("Product not fount")))
                .flatMap(product -> {
                    var res1 = transactionRepository.findByIdProduct(product.getId())
                            .toList()
                            .flatMap(lisTransations -> {
                                Predicate<Transaction> filterwhitCommision = transaction -> transaction.getCommission() > 0.0;
                                var newList = lisTransations.stream()
                                        .filter(filterwhitCommision)
                                        .map(transaction -> TransactionResponse.builder()
                                                .id(transaction.getId())
                                                .feTransaction(transaction.getFeTransaction())
                                                .amount(transaction.getAmount())
                                                .build())
                                        .collect(Collectors.toList());
                                var totalCommission = newList.stream()
                                        .mapToDouble(TransactionResponse::getAmount)
                                        .sum();
                                var report = ReportProductResponse.builder()
                                        .idProduct(product.getId())
                                        .balance(product.getBalance())
                                        .listTransactions(newList)
                                        .build();
                                return Single.just(report);
                            }).toMaybe();
                    return res1;
                });

        return response;
    }

    @Override
    public Maybe<Product> saveAccountPersonalVip(Product product) {

        var response = Maybe.fromPublisher(customerApi.findCustomerById(product.getIdCustomer()))
                .switchIfEmpty(Maybe.error(new ResourceNotFoundException("Product not fount")))
                .flatMap(customer -> {
                    if (!BussinessLogic.isCustomerPersonalVip(customer.getCodTypeCustomer(), customer.getProfile().getCodPerfil())) {
                        return Maybe.error(new AttributeException("Customer is not a Vip"));
                    } else {
                        var res1 = productRepository.existsProductCard(customer.getId())
                                .flatMap(existProduct -> {
                                    if (!existProduct) {
                                        return Single.error(new AttributeException("Card no exist"));
                                    } else {
                                        return saveProductCustomerPersonal(customer, product);
                                    }
                                }).toMaybe();
                        return res1;
                    }
                });

        return response;
    }

    private Single<Product> saveProductCustomerBussines(Product request, CustomerResponse customer) {
        if ((isVerifyCustomerBussinessAccountSuccess(request) && isVerifyCommissionSuccess(request))
                || BussinessLogic.isCustomerEmpresarialPyme(customer.getCodTypeCustomer(), customer.getProfile().getCodPerfil())) {
            if (BussinessLogic.isCustomerEmpresarialPyme(customer.getCodTypeCustomer(), customer.getProfile().getCodPerfil())
                    && BussinessLogic.isServiceAccountSaving(request.getCodTypeService())) {
                return productRepository.existsProductCard(customer.getId())
                        .flatMap(existProduct -> {
                            if (!existProduct) {
                                return Single.error(new AttributeException("Card no exist"));
                            } else {
                                return productRepository.existsProductAccountCurrent(customer.getId())
                                        .flatMap(existProduct2 -> !existProduct2 ? Single.error(new AttributeException("Account current no exist"))
                                                : Single.fromPublisher(productRepository.save(request)));
                            }
                        });
            } else {
                return Single.fromPublisher(productRepository.save(request));
            }
        } else {
            return Single.error(new AttributeException("Operacion no disponible"));
        }
    }

    private Single<Product> saveProductCustomerPersonal(CustomerResponse customer, Product request) {
        return Observable.fromPublisher(productRepository.findByCustomer(request.getIdCustomer()))
                .filter(product -> product.getCodTypeService().equals(request.getCodTypeService()))
                .isEmpty()
                .flatMap(isValue -> {
                    if (isValue) {
                        if (isVerifyCommissionSuccess(request)) {
                            if (BussinessLogic.isCustomerPersonalVip(customer.getCodTypeCustomer(), customer.getProfile().getCodPerfil())
                                    && BussinessLogic.isServiceAccountSaving(request.getCodTypeService())) {
                                return productRepository.existsProductCard(customer.getId())
                                        .flatMap(existProduct -> !existProduct ? Single.error(new AttributeException("Card no exist"))
                                                : Single.fromPublisher(productRepository.save(request)));
                            } else {
                                return Single.fromPublisher(productRepository.save(request));
                            }
                        } else {
                            return Single.error(new AttributeException("Invalid commission"));
                        }
                    } else {
                        return Single.error(new AttributeException("Client already has registered accounts"));
                    }
                });
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
