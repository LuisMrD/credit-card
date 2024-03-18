package dev.luisoliveira.services.useCasesImpl;

import dev.luisoliveira.entities.CreditCardBillEntity;
import dev.luisoliveira.repositories.CreditCardBillRepository;
import dev.luisoliveira.services.useCases.SetOpenForCreditCardBillUseCase;

import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class SetOpenForCreditCardBillUseCaseImpl implements SetOpenForCreditCardBillUseCase {

    CreditCardBillRepository creditCardBillRepository;

    public SetOpenForCreditCardBillUseCaseImpl(CreditCardBillRepository creditCardBillRepository) {
        this.creditCardBillRepository = creditCardBillRepository;
    }

    @Override
    public void setIsOpen(Long id, Boolean isOpen) {

        Optional<CreditCardBillEntity> creditCardBill = creditCardBillRepository.findById(id);

        if(creditCardBill.isPresent()){
            creditCardBill.get().setOpen(isOpen);
            creditCardBillRepository.save(creditCardBill.get());
        }

    }
}
