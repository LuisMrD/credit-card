package dev.luisoliveira.repositories;

import dev.luisoliveira.entities.CreditCardBillEntity;
import dev.luisoliveira.entities.PaymentTransactionEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import java.time.Month;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransactionEntity, Long> {

    Optional<PaymentTransactionEntity> findById(Long id);

    List<PaymentTransactionEntity> findAllByCreditCardBillId(Long creditCardBillId);

}
