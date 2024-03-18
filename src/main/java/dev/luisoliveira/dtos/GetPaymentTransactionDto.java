package dev.luisoliveira.dtos;

import io.micronaut.core.annotation.Introspected;
import lombok.NoArgsConstructor;

import java.time.Month;
import java.time.Year;

@Introspected
@NoArgsConstructor
public class GetPaymentTransactionDto {

    Month month;
    Year year;
    String accountNumber;

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
