package com.bank.service.impl;

import com.bank.enums.TypeTransaction;
import com.bank.exception.AttributeException;
import com.bank.exception.ResourceNotFoundException;
import com.bank.model.entity.Product;
import com.bank.model.entity.Transaction;
import com.bank.model.response.CommissionResponse;
import com.bank.repository.ProductRepository;
import com.bank.repository.TransactionRepository;
import com.bank.service.TransactionService;
import com.bank.util.BussinessRules;
import com.bank.util.ConstantUtil;
import com.bank.util.DateUtil;
import com.bank.util.ProductRule;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final ProductRepository productRepository;

    @Override
    public Single<Transaction> save(Transaction transaction) {

        var response = Maybe.fromPublisher(productRepository.findById(transaction.getIdProduct()))
                .switchIfEmpty(Single.error(new ResourceNotFoundException("Product no found")))
                .flatMap(product -> {
                    return saveTransaction(product, transaction);
                });

        return response;
    }

    @Override
    public Observable<Transaction> findAllByIdProduct(String idProduct) {
        return Observable.fromPublisher(transactionRepository.findAllByIdProduct(idProduct));
    }

    @Override
    public Flowable<CommissionResponse> findAllTransactionWithCommision(String idProduct) {

        return Flowable.fromPublisher(transactionRepository.findByFeTransaction(
                DateUtil.firstDayMonthCurrent(), DateUtil.lastDateMonthCurrent(), idProduct))
                .filter(transaction -> transaction.getCommission() > 0.0)
                .map(transaction -> CommissionResponse.builder()
                        .idTransacction(transaction.getId())
                        .commission(transaction.getCommission())
                        .build()
                );
    }

    private Single<List<Transaction>> getTransactionByMounth(String idProduct) {
        LocalDate today = LocalDate.now();
        LocalDate firstDay = today.withDayOfMonth(1);
        LocalDate lastDay = today.withDayOfMonth(today.lengthOfMonth());

        var response = Observable.
                fromPublisher(transactionRepository.findByFeTransaction(firstDay, lastDay, idProduct))
                .toList();

        return response;

    }

    private Single<Transaction> saveTransaction(Product product, Transaction transaction) {
        var response = getTransactionByMounth(product.getId())
                .flatMap(lista -> {
                    ProductRule productRule = BussinessRules.getRule(product.getCodTypeService());

                    if (lista.size() < productRule.getNumTrans() && lista.size() < productRule.getLimitMaxTrans()) {

                        return transactionRepository.countTransactionByIdProduct(product.getId())
                                .flatMap(countTrans -> {
                                    if (countTrans >= ConstantUtil.NUMBER_MAX_TRANSACCIONES_FREE) {
                                        transaction.setCommission(ConstantUtil.MOUNT_COMMISSION_TRANSACTION);
                                    }
                                    updateProductInMemory(product, transaction);
                                    if (product.getBalance() >= 0.0) {
                                        return Single.fromPublisher(productRepository.save(product))
                                                .flatMap(productSave -> {
                                                    return Single.fromPublisher(transactionRepository.save(transaction));
                                                });
                                    } else {
                                        return Single.error(new AttributeException("Invalid amount"));
                                    }
                                });
                    } else {
                        return Single.error(new AttributeException("You have exceeded the number of transactions"));
                    }

                });
        return response;
    }



    private void updateProductInMemory(Product product, Transaction transaction) {
        if (transaction.getCodTypeTransaction().equals(TypeTransaction.DEPOSITO.getValue())) {
            product.setBalance(product.getBalance() + transaction.getAmount() - transaction.getCommission());
        } else {
            product.setBalance(product.getBalance() - transaction.getAmount() - transaction.getCommission());
        }
    }
}
