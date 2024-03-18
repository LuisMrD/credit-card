package dev.luisoliveira.services.useCasesImpl;

import dev.luisoliveira.dtos.CloseCreditCardBillDto;
import dev.luisoliveira.entities.CreditCardBillEntity;
import dev.luisoliveira.repositories.CreditCardBillRepository;
import dev.luisoliveira.services.useCases.CloseCreditCardBill;

import javax.inject.Singleton;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

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

        List<CreditCardBillEntity> list = creditCardBillRepository.findAll();

        if(creditCardBill.isPresent()){
            creditCardBill.get().setOpen(false);
            creditCardBillRepository.update(creditCardBill.get());

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
                currentCreditCardBill.getMonth() == Month.DECEMBER ? currentCreditCardBill.getYear().plusYears(1) : currentCreditCardBill.getYear());

        if(creditCardBill.isPresent()){
            creditCardBill.get().setOpen(true);
            creditCardBillRepository.save(creditCardBill.get());
        }
    }


}
