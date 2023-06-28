package com.bank.service.impl;

import com.bank.exception.AttributeException;
import com.bank.exception.ResourceNotFoundException;
import com.bank.model.entity.Transaction;
import com.bank.model.request.TransferRequest;
import com.bank.repository.ProductRepository;
import com.bank.repository.TransactionRepository;
import com.bank.service.TransferAccountService;
import com.bank.util.DateUtil;
import io.reactivex.rxjava3.core.Maybe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransferAccountServiceImpl implements TransferAccountService {

    private final TransactionRepository transactionRepository;
    private final ProductRepository productRepository;

    @Override
    public Maybe<Transaction> saveTransferAccount(String idAccountSource, String idAccountDest, TransferRequest transferRequest) {

        var response = Maybe.fromPublisher(productRepository.findById(idAccountSource))
                .switchIfEmpty(Maybe.error(new ResourceNotFoundException("Not found Account source")))
                .flatMap(accountSource -> {
                    double balanceAccountSource = accountSource.getBalance() - transferRequest.getAmount();
                    accountSource.setBalance(balanceAccountSource);
                    return accountSource.getBalance() <= 0.0 ? Maybe.error(new AttributeException("Insufficient funds"))
                            : Maybe.fromPublisher(productRepository.findById(idAccountDest))
                            .switchIfEmpty(Maybe.error(new ResourceNotFoundException("Not found Account destination")))
                            .flatMap(accountDest -> {
                                accountDest.setBalance(accountDest.getBalance() + transferRequest.getAmount());
                               return Maybe.fromPublisher(productRepository.save(accountSource))
                                        .flatMap(productSource -> Maybe.fromPublisher(productRepository.save(accountDest)))
                                        .flatMap(productDest -> {
                                            var transferEntityOut = Transaction.builder()
                                                    .idProduct(idAccountSource)
                                                    .amount(transferRequest.getAmount())
                                                    .feTransaction(DateUtil.convertStringToLocalDate(transferRequest.getDate(), "yyyy-MM-dd"))
                                                    .codTypeTransaction("02")
                                                    .desTypeTransaction("retiro")
                                                    .build();
                                            return Maybe.fromPublisher(transactionRepository.save(transferEntityOut))
                                                    .flatMap(transfertSaveOut -> {
                                                        var transferEntityIn = Transaction.builder()
                                                                .idProduct(idAccountDest)
                                                                .amount(transferRequest.getAmount())
                                                                .feTransaction(DateUtil.convertStringToLocalDate(transferRequest.getDate(), "yyyy-MM-dd"))
                                                                .codTypeTransaction("01")
                                                                .desTypeTransaction("ingreso")
                                                                .build();

                                                        return Maybe.fromPublisher(transactionRepository.save(transferEntityIn));
                                                    });
                                        });
                            });
                });

        return response;
    }
}
