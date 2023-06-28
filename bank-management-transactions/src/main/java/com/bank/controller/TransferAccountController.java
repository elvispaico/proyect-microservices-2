package com.bank.controller;

import com.bank.model.request.TransferRequest;
import com.bank.model.response.MessageResponse;
import com.bank.service.TransferAccountService;
import io.reactivex.rxjava3.core.Maybe;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/bank/transfers")
public class TransferAccountController {

    private final TransferAccountService transferAccountService;

    @PostMapping(value = "/account-source/{idAccountFrom}/account-destination/{idAccountDest}")
    public Maybe<ResponseEntity<MessageResponse>> saveTransfer(@PathVariable String idAccountDest,
                                                               @PathVariable String idAccountFrom,
                                                               @RequestBody TransferRequest request) {
        return transferAccountService.saveTransferAccount(idAccountFrom, idAccountDest, request)
                .map(transaction -> new ResponseEntity<>(
                        new MessageResponse(HttpStatus.OK.value(), "Transfer succes"),
                        HttpStatus.OK
                ));
    }

}
