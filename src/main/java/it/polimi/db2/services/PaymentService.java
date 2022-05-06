package it.polimi.db2.services;

import it.polimi.db2.entities.*;

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

    public FailedPayment addFailedPayment(Date date, float amount, UserCustomer name, Orders order){
        FailedPayment failedPayment = new FailedPayment();
        failedPayment.setAmount(amount);
        failedPayment.setDateTime(date);
        failedPayment.setFailedUser(name);
        failedPayment.setOrder(order);
        em.persist(failedPayment);
        em.flush();
        return failedPayment;
    }

    public void updateFailedPayments(UserCustomer userCustomer, Orders order){

        List<FailedPayment> payments = em.createNamedQuery("FailedPayment.getFailedPaymentByOrder", FailedPayment.class).setParameter("order", order).getResultList();
        if(payments.size() != 0 ){
            em.remove(payments.get(0));
        }

    }
    public void addAuditingTable(UserCustomer user, String email, float TotalCost, Date dateLastRejection){
        AuditingTable auditingTable = new AuditingTable();
        List<FailedPayment> failedPayments = getFailedPaymentsOfUser(user);
        float total = 0;
        for(FailedPayment fp : failedPayments){
            total = total + fp.getAmount();
        }
        auditingTable.setAmount(total);
        auditingTable.setDate(dateLastRejection);
        auditingTable.setEmail(email);
        auditingTable.setUsername(user);
        em.persist(auditingTable);

    }
    public void updateAuditingTable(UserCustomer user, String email, float TotalCost, Date dateLastRejection, boolean accepted) {
        List<AuditingTable> auditingTable = em.createNamedQuery("AuditingTable.findAuditingTableByUser", AuditingTable.class).setParameter("username", user).getResultList();

        if (auditingTable.size() != 0) {
            float oldAmount = auditingTable.get(0).getAmount();
            if (accepted) {
                auditingTable.get(0).setAmount(oldAmount - TotalCost);
            } else {
                List<FailedPayment> failedPayments = getFailedPaymentsOfUser(user);
                float total = 0;
                for(FailedPayment fp : failedPayments){
                    total = fp.getAmount();
                }
                auditingTable.get(0).setAmount(total);
            }
            auditingTable.get(0).setDate(dateLastRejection);
        }
    }


    public void deleteAlert(UserCustomer user){
        List<AuditingTable> userAuditingTable = em.createNamedQuery(  "AuditingTable.findAuditingTableByUser" ,AuditingTable.class).setParameter("username", user).getResultList();
        if(userAuditingTable.size() != 0 ){
            em.remove(userAuditingTable.get(0));
        }
    }


}
