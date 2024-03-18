package dev.luisoliveira.dtos;

import dev.luisoliveira.entities.AccountEntity;
import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Introspected
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountDto {

    @NotNull(message = "name is required")
    @Size( min = 6, message = "name size is invalid")
    String name;

    @NotNull(message = "document is required")
    @Size( min = 11, max = 11, message = "document size is invalid")
    String document;

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

    public static CreateAccountDto of(AccountEntity accountEntity){
        CreateAccountDto accountDto = new CreateAccountDto();
        accountDto.setName(accountEntity.getName());
        accountDto.setDocument(accountEntity.getDocument());

        return accountDto;
    }

}
