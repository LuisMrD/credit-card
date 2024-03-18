package dev.luisoliveira.services.useCasesImpl;

import dev.luisoliveira.dtos.AccountDto;
import dev.luisoliveira.repositories.AccountRepository;
import dev.luisoliveira.services.useCases.GetAccountUseCase;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GetAccountUseCaseImpl implements GetAccountUseCase {


    AccountRepository accountRepository;

    public GetAccountUseCaseImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto getAccountByAccountNumber(String accountNumber) {
        return AccountDto.of(accountRepository.findByAccountNumber(accountNumber));
    }
}
