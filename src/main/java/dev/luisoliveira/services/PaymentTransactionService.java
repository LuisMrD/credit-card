package dev.luisoliveira.services;

import dev.luisoliveira.dtos.PaymentTransactionDto;
import dev.luisoliveira.services.useCases.GetCreditCardBillUseCase;
import dev.luisoliveira.services.useCasesImpl.CreateCreditCardBillUseCaseImpl;
import dev.luisoliveira.services.useCasesImpl.CreatePaymentTransactionUseCaseImpl;
import dev.luisoliveira.services.useCasesImpl.GetCreditCardBillUseCaseImpl;

import javax.inject.Singleton;

@Singleton
public class PaymentTransactionService {

    AccountService accountService;
    CreatePaymentTransactionUseCaseImpl createPaymentTransactionUseCase;
    CreateCreditCardBillUseCaseImpl createCreditCardBillUseCase;
    GetCreditCardBillUseCaseImpl getCreditCardBillUseCase;

    public PaymentTransactionService(AccountService accountService, CreatePaymentTransactionUseCaseImpl createPaymentTransactionUseCase, CreateCreditCardBillUseCaseImpl createCreditCardBillUseCase, GetCreditCardBillUseCaseImpl getCreditCardBillUseCase) {
        this.accountService = accountService;
        this.createPaymentTransactionUseCase = createPaymentTransactionUseCase;
        this.createCreditCardBillUseCase = createCreditCardBillUseCase;
        this.getCreditCardBillUseCase = getCreditCardBillUseCase;
    }

    public Long setCreatePaymentTransaction(PaymentTransactionDto paymentTransactionDto){
        return createPaymentTransactionUseCase.createNewPaymentTransaction(paymentTransactionDto, createCreditCardBillUseCase, getCreditCardBillUseCase).getCreditCardBillId();
    }


}
