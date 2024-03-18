package dev.luisoliveira.controllers;

import dev.luisoliveira.dtos.*;
import dev.luisoliveira.services.PaymentTransactionService;
import dev.luisoliveira.services.useCases.GetPaymentTransactionsUseCase;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;

import javax.validation.Valid;
import java.util.List;

@Controller("/payment")
public class PaymentTransactionController {

    PaymentTransactionService paymentTransactionService;

    public PaymentTransactionController(PaymentTransactionService paymentTransactionService) {
        this.paymentTransactionService = paymentTransactionService;
    }

    @Post
    public HttpResponse<OpenBillDto> createNewAccount(@Body @Valid PaymentTransactionDto paymentTransactionDto){

        return HttpResponse.created(new OpenBillDto(paymentTransactionService.setCreatePaymentTransaction(paymentTransactionDto)));
    }


}
