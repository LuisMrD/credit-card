package dev.luisoliveira.services.useCases;

import dev.luisoliveira.dtos.CloseCreditCardBillDto;

public interface CloseCreditCardBill {

    void closeCreditCardBill(CloseCreditCardBillDto closeCreditCardBillDto);

    void closeAllBillsIfIsTheLastDayOfTheMonth();
}
