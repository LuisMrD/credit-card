package dev.luisoliveira.services.useCasesImpl;

import dev.luisoliveira.entities.CreditCardBillEntity;
import dev.luisoliveira.repositories.CreditCardBillRepository;
import dev.luisoliveira.services.useCases.CreateCreditCardBillUseCase;

import javax.inject.Singleton;
import java.time.Month;
import java.time.Year;
import java.util.Optional;

@Singleton
public class CreateCreditCardBillUseCaseImpl implements CreateCreditCardBillUseCase {

    CreditCardBillRepository creditCardBillRepository;
    GetCreditCardBillUseCaseImpl getCreditCardBillUseCase;

    public CreateCreditCardBillUseCaseImpl(CreditCardBillRepository creditCardBillRepository, GetCreditCardBillUseCaseImpl getCreditCardBillUseCase) {
        this.creditCardBillRepository = creditCardBillRepository;
        this.getCreditCardBillUseCase = getCreditCardBillUseCase;
    }

    @Override
    public CreditCardBillEntity createNewCreditCardBill(String accountNumber, Month month, Year year) {

        Optional<CreditCardBillEntity> creditCardBill = getCreditCardBillUseCase.findCreditCardBillByMonthAndYearAndAccountNumber(accountNumber,
                month, year);

        CreditCardBillEntity newCreditCardBill = new CreditCardBillEntity();

        if (creditCardBill.isPresent()){
            if(creditCardBill.get().getOpen()){
                return creditCardBill.get();
            } else {
                newCreditCardBill = creditCardBillWithCurrentMonthPlusOne(accountNumber, month, year);
            }
        }

        if(creditCardBill.isEmpty()){
            newCreditCardBill = creditCardBillWithCurrentMonth(accountNumber, month, year);
        }

        return newCreditCardBill;
    }

    private CreditCardBillEntity creditCardBillWithCurrentMonth(String accountNumber, Month month, Year year){
        CreditCardBillEntity newCreditCardBill = new CreditCardBillEntity();
        newCreditCardBill.setAccountNumber(accountNumber);
        newCreditCardBill.setMonth(month);
        newCreditCardBill.setYear(year);

        return creditCardBillRepository.save(newCreditCardBill);
    }

    private CreditCardBillEntity creditCardBillWithCurrentMonthPlusOne(String accountNumber, Month month, Year year){
        CreditCardBillEntity newCreditCardBill = new CreditCardBillEntity();
        newCreditCardBill.setAccountNumber(accountNumber);
        newCreditCardBill.setMonth(month.plus(1));
        newCreditCardBill.setYear(year);
        newCreditCardBill.setOpen(true);

        return creditCardBillRepository.save(newCreditCardBill);
    }
}
