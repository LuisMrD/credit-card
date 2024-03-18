package dev.luisoliveira.controllers;

import dev.luisoliveira.dtos.*;
import dev.luisoliveira.services.CreditCardBIllService;
import dev.luisoliveira.services.useCasesImpl.GetPaymentTransactionsUseCaseImpl;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;

import javax.validation.Valid;
import java.time.Month;
import java.time.Year;
import java.util.List;

@Controller("/creditCardBill")
public class CreditCardBillController {

    CreditCardBIllService creditCardBIllService;
    GetPaymentTransactionsUseCaseImpl getPaymentTransactionsUseCase;

    public CreditCardBillController(CreditCardBIllService creditCardBIllService, GetPaymentTransactionsUseCaseImpl getPaymentTransactionsUseCase) {
        this.creditCardBIllService = creditCardBIllService;
        this.getPaymentTransactionsUseCase = getPaymentTransactionsUseCase;
    }

    @Patch
    public HttpResponse<HttpStatus> createNewAccount(@Body @Valid CloseCreditCardBillDto closeCreditCardBillDto){
        creditCardBIllService.closeCreditCardBill(closeCreditCardBillDto);
        return HttpResponse.ok();
    }

    @Get("/{accountNumber}/{month}/{year}")
    public HttpResponse<List<ListPaymentTransactionDto>> createNewAccount(String accountNumber, String month, String year){
        return HttpResponse.ok(getPaymentTransactionsUseCase.getPaymentTransactionDtoList(accountNumber,
                Month.of(Integer.parseInt(month)), Year.of(Integer.parseInt(year))));
    }
}
