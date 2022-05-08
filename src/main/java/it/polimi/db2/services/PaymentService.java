package it.polimi.db2.services;

import it.polimi.db2.entities.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Stateless
public class PaymentService {

    @PersistenceContext( unitName = "databaseEJB" )
    private EntityManager em;

    @EJB(name = "services/OrderService")
    private OrderService orderService;

    public PaymentService(){}

    public List<FailedPayment> getFailedPaymentsOfUser(UserCustomer name){
        List<FailedPayment> failedPaymentList = em.createNamedQuery("FailedPayment.getFailedPaymentsOfUser", FailedPayment.class).setParameter("name", name).getResultList();
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
            payments.forEach(fp -> {
                em.remove(fp);
            });
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
        auditingTable.setCustomer(user);
        em.persist(auditingTable);

    }
    public void updateAuditingTable(UserCustomer user, String email, float TotalCost, Date dateLastRejection, boolean accepted) {
        List<AuditingTable> auditingTable = em.createNamedQuery("AuditingTable.findAuditingTableByUser", AuditingTable.class).setParameter("customer", user).getResultList();

        if (auditingTable.size() != 0) {
            float oldAmount = auditingTable.get(0).getAmount();
            if (accepted) {
                auditingTable.get(0).setAmount(oldAmount - TotalCost);
            } else {
                List<Orders> orders = orderService.getNotValidOrdersOfUser(user);
                float total = 0;
                for(Orders or : orders){
                    total = or.getTotalCost();
                }
                auditingTable.get(0).setAmount(total);
            }
            auditingTable.get(0).setDate(dateLastRejection);
        }
    }


    public void deleteAlert(UserCustomer user){
       List<AuditingTable> userAuditingTable = em.createNamedQuery(  "AuditingTable.findAuditingTableByUser" ,AuditingTable.class).setParameter("customer", user).getResultList();
        if(userAuditingTable.size() != 0 ){
            userAuditingTable.forEach(ua -> em.remove(ua));
            em.remove(userAuditingTable.get(0));
        }
    }
    public boolean checkAuditingTable(UserCustomer user){
        List<AuditingTable> auditingTable = em.createNamedQuery("AuditingTable.findAuditingTableByUser", AuditingTable.class).setParameter("customer", user).getResultList();
        if(auditingTable.size() == 0){
            return false;
        } else return true;
    }



}
