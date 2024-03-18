package dev.luisoliveira.dtos;

import io.micronaut.core.annotation.Introspected;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Introspected
@NoArgsConstructor
public class CloseCreditCardBillDto {

    @NotNull(message = "credit card bill id is required")
    private Long creditCardBillId;

    @NotNull(message = "account number is required")
    private String accountNumber;

    public Long getCreditCardBillId() {
        return creditCardBillId;
    }

    public void setCreditCardBillId(Long creditCardBillId) {
        this.creditCardBillId = creditCardBillId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
