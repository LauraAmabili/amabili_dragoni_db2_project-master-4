package it.polimi.db2.external.services;

public class BillingService {

    public BillingService() {
        super();
    }


    public boolean attemptPayment(boolean makePaymentFail) {

        return !makePaymentFail;

    }
}
