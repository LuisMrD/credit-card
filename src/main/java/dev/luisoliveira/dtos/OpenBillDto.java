package dev.luisoliveira.dtos;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OpenBillDto {

    Long OpenCreditCardBillId;

    public OpenBillDto(Long openCreditCardBillId) {
        OpenCreditCardBillId = openCreditCardBillId;
    }

    public Long getId() {
        return OpenCreditCardBillId;
    }

    public void setId(Long OpenCreditCardBillId) {
        this.OpenCreditCardBillId = OpenCreditCardBillId;
    }
}
