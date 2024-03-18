package dev.luisoliveira.services.useCasesImpl;

import dev.luisoliveira.dtos.CloseCreditCardBillDto;
import dev.luisoliveira.entities.CreditCardBillEntity;
import dev.luisoliveira.repositories.CreditCardBillRepository;
import dev.luisoliveira.services.useCases.CloseCreditCardBill;

import javax.inject.Singleton;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

@Singleton
public class CloseCreditCardBillImpl implements CloseCreditCardBill {

    GetCreditCardBillUseCaseImpl getCreditCardBillUseCase;
    CreditCardBillRepository creditCardBillRepository;

    public CloseCreditCardBillImpl(GetCreditCardBillUseCaseImpl getCreditCardBillUseCase, CreditCardBillRepository creditCardBillRepository) {
        this.getCreditCardBillUseCase = getCreditCardBillUseCase;
        this.creditCardBillRepository = creditCardBillRepository;
    }

    @Override
    public void closeCreditCardBill(CloseCreditCardBillDto closeCreditCardBillDto) {
        Optional<CreditCardBillEntity> creditCardBill = getCreditCardBillUseCase.findByIdAndAccountNumber(closeCreditCardBillDto.getCreditCardBillId()
                , closeCreditCardBillDto.getAccountNumber());

        if(creditCardBill.isPresent()){
            creditCardBill.get().setOpen(false);
            creditCardBillRepository.save(creditCardBill.get());

            openNextCreditCardBillIfExists(creditCardBill.get());
        }
    }

    @Override
    public void closeAllBillsIfIsTheLastDayOfTheMonth() {

        LocalDate now = LocalDate.now();
        LocalDate lastDay = now.with(TemporalAdjusters.lastDayOfMonth());

        if(now.getDayOfMonth() == lastDay.getDayOfMonth()){
            List<CreditCardBillEntity> creditCardBillEntityList = creditCardBillRepository.findAllByMonth(now.getMonth());

            for(CreditCardBillEntity openBill : creditCardBillEntityList){
                openBill.setOpen(false);
                creditCardBillRepository.save(openBill);
                openNextCreditCardBillIfExists(openBill);
            }
        }
    }

    private void openNextCreditCardBillIfExists(CreditCardBillEntity currentCreditCardBill){
        Optional<CreditCardBillEntity> creditCardBill = getCreditCardBillUseCase.findCreditCardBillByMonthAndYearAndAccountNumber(currentCreditCardBill.getAccountNumber(),
                currentCreditCardBill.getMonth().plus(1),
                currentCreditCardBill.getYear());

        if(creditCardBill.isPresent()){
            creditCardBill.get().setOpen(true);
            creditCardBillRepository.save(creditCardBill.get());
        }
    }


}
