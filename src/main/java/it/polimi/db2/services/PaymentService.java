package it.polimi.db2.services;

import it.polimi.db2.entities.FailedPayment;
import it.polimi.db2.entities.InternetService;
import it.polimi.db2.entities.UserCustomer;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Stateless
public class PaymentService {

    @PersistenceContext( unitName = "databaseEJB" )
    private EntityManager em;

    public PaymentService(){}

    public List<FailedPayment> getFailedPaymentsOfUser(UserCustomer name){

        List<FailedPayment> failedPaymentList = em.createNamedQuery("FailedPayment.getFailedPaymentsOfUser", FailedPayment.class).setParameter("name", name).getResultList();
        if(failedPaymentList.isEmpty()) return null;
        return failedPaymentList;
    }

    public FailedPayment addFailedPayment(Date date, float amount, UserCustomer name){
        FailedPayment failedPayment = new FailedPayment();
        failedPayment.setAmount(amount);
        failedPayment.setDateTime(date);
        failedPayment.setFailedUser(name);
        em.persist(failedPayment);
        em.flush();
        return failedPayment;

    }
}
