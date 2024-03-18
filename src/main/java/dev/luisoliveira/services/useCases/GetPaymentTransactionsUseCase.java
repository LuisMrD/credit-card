package dev.luisoliveira.services.useCases;

import dev.luisoliveira.dtos.ListPaymentTransactionDto;

import java.time.Month;
import java.time.Year;
import java.util.List;

public interface GetPaymentTransactionsUseCase {

    List<ListPaymentTransactionDto> getPaymentTransactionDtoList(String accountNumber, Month month, Year year);
}
