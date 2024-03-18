package dev.luisoliveira.services.useCases;

import dev.luisoliveira.dtos.PaymentTransactionDto;
import dev.luisoliveira.entities.PaymentTransactionEntity;

public interface CreatePaymentTransactionUseCase {

    PaymentTransactionEntity createNewPaymentTransaction(PaymentTransactionDto paymentTransactionDto,
                                                         CreateCreditCardBillUseCase createCreditCardBillUseCase, GetCreditCardBillUseCase getCreditCardBillUseCase);
}
