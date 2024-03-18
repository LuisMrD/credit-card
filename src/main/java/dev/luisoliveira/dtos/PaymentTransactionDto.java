package dev.luisoliveira.dtos;

import io.micronaut.core.annotation.Introspected;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Introspected
@NoArgsConstructor
public class PaymentTransactionDto {

    @NotNull(message = "business establishment is required")
    @Size( min = 3, message = "business establishment name size is invalid")
    private String businessEstablishment;

    @NotNull(message = "value is required")
    private Double value;

    @NotNull(message = "value of installments establishment is required")
    private Double valueOfInstallments;

    @NotNull(message = "number of installments is required")
    private Integer numberOfInstallments;

    @NotNull(message = "account number is required")
    private String accountNumber;

    @NotNull(message = "transaction date is required (dd/MM/yyyy)")
    private Date transactionDate;

    public String getBusinessEstablishment() {
        return businessEstablishment;
    }

    public void setBusinessEstablishment(String businessEstablishment) {
        this.businessEstablishment = businessEstablishment;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getValueOfInstallments() {
        return valueOfInstallments;
    }

    public void setValueOfInstallments(Double valueOfInstallments) {
        this.valueOfInstallments = valueOfInstallments;
    }

    public Integer getNumberOfInstallments() {
        return numberOfInstallments;
    }

    public void setNumberOfInstallments(Integer numberOfInstallments) {
        this.numberOfInstallments = numberOfInstallments;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        this.transactionDate = sdf.parse(transactionDate);
    }
}
