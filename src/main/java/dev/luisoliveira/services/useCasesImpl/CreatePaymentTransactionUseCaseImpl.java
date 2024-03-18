package dev.luisoliveira.services.useCasesImpl;

import dev.luisoliveira.dtos.PaymentTransactionDto;
import dev.luisoliveira.entities.CreditCardBillEntity;
import dev.luisoliveira.entities.PaymentTransactionEntity;
import dev.luisoliveira.repositories.PaymentTransactionRepository;
import dev.luisoliveira.services.useCases.CreateCreditCardBillUseCase;
import dev.luisoliveira.services.useCases.CreatePaymentTransactionUseCase;
import dev.luisoliveira.services.useCases.GetCreditCardBillUseCase;
import dev.luisoliveira.services.useCases.SetOpenForCreditCardBillUseCase;

import javax.inject.Singleton;
import java.time.Month;
import java.time.Year;
import java.util.Calendar;

@Singleton
public class CreatePaymentTransactionUseCaseImpl implements CreatePaymentTransactionUseCase {

    PaymentTransactionRepository paymentTransactionRepository;
    SetOpenForCreditCardBillUseCaseImpl setOpenForCreditCardBillUseCase;

    public CreatePaymentTransactionUseCaseImpl(PaymentTransactionRepository paymentTransactionRepository, SetOpenForCreditCardBillUseCaseImpl setOpenForCreditCardBillUseCase) {
        this.paymentTransactionRepository = paymentTransactionRepository;
        this.setOpenForCreditCardBillUseCase = setOpenForCreditCardBillUseCase;
    }

    @Override
    public PaymentTransactionEntity createNewPaymentTransaction(PaymentTransactionDto paymentTransactionDto, CreateCreditCardBillUseCase createCreditCardBillUseCase, GetCreditCardBillUseCase getCreditCardBillUseCase) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(paymentTransactionDto.getTransactionDate());

        PaymentTransactionEntity newPaymentTransactionEntity = new PaymentTransactionEntity();

        if(paymentTransactionDto.getNumberOfInstallments() > 1){

            newPaymentTransactionEntity = generateTransactionsForPurchaseInInstallments(paymentTransactionDto, createCreditCardBillUseCase, calendar);

        } else {

            CreditCardBillEntity creditCardBill = createCreditCardBillUseCase.createNewCreditCardBill(paymentTransactionDto.getAccountNumber(),
                    Month.of(calendar.get(Calendar.MONTH) + 1), Year.of(calendar.get(Calendar.YEAR)));

            setOpenForCreditCardBillUseCase.setIsOpen(creditCardBill.getId(), true);

            PaymentTransactionEntity paymentTransactionEntity = createPaymentTransactionEntity(paymentTransactionDto);
            paymentTransactionEntity.setNumberOfCurrentInstallment(paymentTransactionDto.getNumberOfInstallments());
            paymentTransactionEntity.setCreditCardBillId(creditCardBill.getId());

            newPaymentTransactionEntity = paymentTransactionRepository.save(paymentTransactionEntity);
        }

        return newPaymentTransactionEntity;
    }

    private PaymentTransactionEntity generateTransactionsForPurchaseInInstallments(PaymentTransactionDto paymentTransactionDto,
                                                               CreateCreditCardBillUseCase createCreditCardBillUseCase,
                                                               Calendar calendar){

        PaymentTransactionEntity paymentTransaction = new PaymentTransactionEntity();

        for(int i = 1; i <= paymentTransactionDto.getNumberOfInstallments(); i++){

            if( i > 1){
                calendar.add(Calendar.MONTH, 1);
            }

            CreditCardBillEntity creditCardBill = createCreditCardBillUseCase.createNewCreditCardBill(paymentTransactionDto.getAccountNumber(),
                    Month.of(calendar.get(Calendar.MONTH) + 1), Year.of(calendar.get(Calendar.YEAR)));

            PaymentTransactionEntity paymentTransactionEntity = createPaymentTransactionEntity(paymentTransactionDto);
            paymentTransactionEntity.setNumberOfCurrentInstallment(i);
            paymentTransactionEntity.setCreditCardBillId(creditCardBill.getId());

            if(i == 1){
                paymentTransaction = paymentTransactionRepository.save(paymentTransactionEntity);
                setOpenForCreditCardBillUseCase.setIsOpen(creditCardBill.getId(), true);

            }else{
                paymentTransactionRepository.save(paymentTransactionEntity);
            }
        }

        return paymentTransaction;
    }

    private PaymentTransactionEntity createPaymentTransactionEntity(PaymentTransactionDto paymentTransactionDto){

        PaymentTransactionEntity paymentTransactionEntity = new PaymentTransactionEntity();
        paymentTransactionEntity.setTransactionDate(paymentTransactionDto.getTransactionDate());
        paymentTransactionEntity.setAccountNumber(paymentTransactionDto.getAccountNumber());
        paymentTransactionEntity.setBusinessEstablishment(paymentTransactionDto.getBusinessEstablishment());
        paymentTransactionEntity.setValue(paymentTransactionDto.getValue());
        paymentTransactionEntity.setValueOfInstallments(paymentTransactionDto.getValueOfInstallments());
        paymentTransactionEntity.setNumberOfCurrentInstallment(paymentTransactionDto.getNumberOfInstallments());
        paymentTransactionEntity.setNumberOfInstallments(paymentTransactionDto.getNumberOfInstallments());

        return paymentTransactionEntity;
    }

}
