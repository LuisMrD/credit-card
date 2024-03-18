package dev.luisoliveira.services.useCases;

import dev.luisoliveira.entities.CreditCardBillEntity;

import java.time.Month;
import java.time.Year;
import java.util.Optional;

public interface GetCreditCardBillUseCase {

    Optional<CreditCardBillEntity> findCreditCardBillByMonthAndYearAndAccountNumberAndOpenTrue(String accountNumber, Month month, Year year);

    Optional<CreditCardBillEntity> findCreditCardBillByMonthAndYearAndAccountNumber(String accountNumber, Month month, Year year);

    Optional<CreditCardBillEntity> findByIdAndAccountNumber(Long id, String accountNumber);

}
