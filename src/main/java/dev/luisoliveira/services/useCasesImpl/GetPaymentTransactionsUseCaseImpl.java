package dev.luisoliveira.services.useCasesImpl;

import dev.luisoliveira.dtos.ListPaymentTransactionDto;
import dev.luisoliveira.entities.CreditCardBillEntity;
import dev.luisoliveira.entities.PaymentTransactionEntity;
import dev.luisoliveira.repositories.CreditCardBillRepository;
import dev.luisoliveira.repositories.PaymentTransactionRepository;
import dev.luisoliveira.services.useCases.GetPaymentTransactionsUseCase;

import javax.inject.Singleton;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class GetPaymentTransactionsUseCaseImpl implements GetPaymentTransactionsUseCase {

    PaymentTransactionRepository paymentTransactionRepository;
    CreditCardBillRepository creditCardBillRepository;

    public GetPaymentTransactionsUseCaseImpl(PaymentTransactionRepository paymentTransactionRepository, CreditCardBillRepository creditCardBillRepository) {
        this.paymentTransactionRepository = paymentTransactionRepository;
        this.creditCardBillRepository = creditCardBillRepository;
    }

    @Override
    public List<ListPaymentTransactionDto> getPaymentTransactionDtoList(String accountNumber, Month month, Year year) {

        Optional<CreditCardBillEntity> creditCardBill = creditCardBillRepository.findByAccountNumberAndMonthAndYear(accountNumber, month, year);

        if(creditCardBill.isEmpty()){
            throw new RuntimeException("Não existem transações dentro do período");
        }

        List<PaymentTransactionEntity> paymentTransactionEntityList = paymentTransactionRepository.findAllByCreditCardBillId(creditCardBill.get().getId());
        return paymentTransactionEntityList.stream().map(ListPaymentTransactionDto::of).collect(Collectors.toList());
    }
}
