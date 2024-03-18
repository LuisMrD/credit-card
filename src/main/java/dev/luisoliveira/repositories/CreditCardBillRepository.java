package dev.luisoliveira.repositories;

import dev.luisoliveira.entities.CreditCardBillEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Optional;

@Repository
public interface CreditCardBillRepository extends JpaRepository<CreditCardBillEntity, Long> {

    Optional<CreditCardBillEntity> findByAccountNumberAndMonthAndYearAndOpenTrue(String accountNumber, Month month, Year year);

    Optional<CreditCardBillEntity> findByAccountNumberAndMonthAndYear(String accountNumber, Month month, Year year);

    Optional<CreditCardBillEntity> findByIdAndAccountNumber(Long id, String accountId);

    List<CreditCardBillEntity> findAllByMonth(Month month);

}
