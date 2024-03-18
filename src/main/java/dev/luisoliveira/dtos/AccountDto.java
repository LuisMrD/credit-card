package dev.luisoliveira.dtos;

import dev.luisoliveira.entities.AccountEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

    Long id;
    String accountNumber;
    String name;
    String document;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public static AccountDto of(AccountEntity accountEntity){
        AccountDto accountDto = new AccountDto();
        accountDto.setId(accountEntity.getId());
        accountDto.setAccountNumber(accountEntity.getAccountNumber());
        accountDto.setName(accountEntity.getName());
        accountDto.setDocument(accountEntity.getDocument());

        return accountDto;
    }

}
