package dev.luisoliveira.services.useCases;

import dev.luisoliveira.entities.CreditCardBillEntity;

import java.time.Month;
import java.time.Year;

public interface CreateCreditCardBillUseCase {

    CreditCardBillEntity createNewCreditCardBill(String accountNumber, Month month, Year year, Boolean isOpen);
}
