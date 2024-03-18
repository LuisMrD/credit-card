package dev.luisoliveira.services.jobs;

import dev.luisoliveira.services.useCasesImpl.CloseCreditCardBillImpl;
import io.micronaut.scheduling.annotation.Scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;

@Singleton
public class CloseCreditCardBillJob {


    CloseCreditCardBillImpl closeCreditCardBill;

    public CloseCreditCardBillJob(CloseCreditCardBillImpl closeCreditCardBill) {
        this.closeCreditCardBill = closeCreditCardBill;
    }

    private static final Logger LOG = LoggerFactory.getLogger(CloseCreditCardBillJob.class);

    //86400s
    @Scheduled(fixedDelay = "86400s")
    void executeEveryDay() {
        closeCreditCardBill.closeAllBillsIfIsTheLastDayOfTheMonth();
        LOG.info("Close bills verification routine");
    }
}
