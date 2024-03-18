package dev.luisoliveira.services.useCasesImpl;

import dev.luisoliveira.entities.CreditCardBillEntity;
import dev.luisoliveira.repositories.CreditCardBillRepository;
import dev.luisoliveira.services.useCases.GetCreditCardBillUseCase;

import javax.inject.Singleton;
import java.time.Month;
import java.time.Year;
import java.util.Optional;

@Singleton
public class GetCreditCardBillUseCaseImpl implements GetCreditCardBillUseCase {

    CreditCardBillRepository creditCardBillRepository;

    public GetCreditCardBillUseCaseImpl(CreditCardBillRepository creditCardBillRepository) {
        this.creditCardBillRepository = creditCardBillRepository;
    }


    @Override
    public Optional<CreditCardBillEntity> findCreditCardBillByMonthAndYearAndAccountNumberAndOpenTrue(String accountNumber, Month month, Year year) {

        Optional<CreditCardBillEntity> creditCardBill = creditCardBillRepository.findByAccountNumberAndMonthAndYearAndOpenTrue(accountNumber, month, year);

        if(creditCardBill.isEmpty()){
            throw new RuntimeException("Não existe fatura aberta para este período");
        }

        return creditCardBill;
    }

    @Override
    public Optional<CreditCardBillEntity> findCreditCardBillByMonthAndYearAndAccountNumber(String accountNumber, Month month, Year year) {
        return creditCardBillRepository.findByAccountNumberAndMonthAndYear(accountNumber, month, year);
    }

    @Override
    public Optional<CreditCardBillEntity> findByIdAndAccountNumber(Long id, String accountNumber) {
        return creditCardBillRepository.findByIdAndAccountNumber(id, accountNumber);
    }
}
