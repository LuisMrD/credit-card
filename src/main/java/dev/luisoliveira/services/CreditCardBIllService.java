package dev.luisoliveira.services;

import dev.luisoliveira.dtos.CloseCreditCardBillDto;
import dev.luisoliveira.entities.CreditCardBillEntity;
import dev.luisoliveira.services.useCasesImpl.CloseCreditCardBillImpl;
import dev.luisoliveira.services.useCasesImpl.CreateCreditCardBillUseCaseImpl;
import dev.luisoliveira.services.useCasesImpl.GetCreditCardBillUseCaseImpl;

import javax.inject.Singleton;
import java.time.Month;
import java.time.Year;
import java.util.Optional;

@Singleton
public class CreditCardBIllService {

    CreateCreditCardBillUseCaseImpl createCreditCardBillUseCase;
    GetCreditCardBillUseCaseImpl getCreditCardBillUseCase;
    CloseCreditCardBillImpl closeCreditCardBillUseCase;

    public CreditCardBIllService(CreateCreditCardBillUseCaseImpl createCreditCardBillUseCase,
                                 GetCreditCardBillUseCaseImpl getCreditCardBillUseCase, CloseCreditCardBillImpl closeCreditCardBillUseCase) {
        this.createCreditCardBillUseCase = createCreditCardBillUseCase;
        this.getCreditCardBillUseCase = getCreditCardBillUseCase;
        this.closeCreditCardBillUseCase = closeCreditCardBillUseCase;
    }

    public CreditCardBillEntity createNewCreditCardBillIfItDoesntExistInThePeriod(String accountNumber, Month month, Year year){

        Optional<CreditCardBillEntity> creditCardBill = getCreditCardBillUseCase.findCreditCardBillByMonthAndYearAndAccountNumberAndOpenTrue(accountNumber, month, year);

        if(creditCardBill.isEmpty()){
            return createCreditCardBillUseCase.createNewCreditCardBill(accountNumber, month, year, true);
        }

        return creditCardBill.get();
    }

    public void closeCreditCardBill(CloseCreditCardBillDto closeCreditCardBillDto){
        closeCreditCardBillUseCase.closeCreditCardBill(closeCreditCardBillDto);
    }

}
