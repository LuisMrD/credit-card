package dev.luisoliveira.services.useCasesImpl;

import dev.luisoliveira.dtos.AccountDto;
import dev.luisoliveira.dtos.CreateAccountDto;
import dev.luisoliveira.entities.AccountEntity;
import dev.luisoliveira.repositories.AccountRepository;
import dev.luisoliveira.services.useCases.CreateAccountUseCase;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Random;

@Singleton
public class CreateAccountUseCaseImpl implements CreateAccountUseCase {

    AccountRepository accountRepository;

    public CreateAccountUseCaseImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(CreateAccountDto createAccountDto) {

        AccountEntity newAccount = new AccountEntity();
        newAccount.setName(createAccountDto.getName());
        newAccount.setDocument(createAccountDto.getDocument());
        newAccount.setAccountNumber(generateAccountNumber());

        return AccountDto.of(accountRepository.save(newAccount));
    }

    private String generateAccountNumber(){
        String accountNumber = "";
        Random random = new Random();

        while(accountNumber.length() < 16){
            accountNumber += String.valueOf(random.nextInt(9));
        }

        return accountNumber;
    }
}
