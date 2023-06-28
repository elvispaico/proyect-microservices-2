package com.bank.service;

import com.bank.model.entity.Transaction;
import com.bank.model.request.TransferRequest;
import io.reactivex.rxjava3.core.Maybe;

public interface TransferAccountService {

    Maybe<Transaction> saveTransferAccount(String idAccountSource, String idAccountDest, TransferRequest request);
}
