package it.polimi.db2.services;

import it.polimi.db2.entities.AuditingTable;
import it.polimi.db2.entities.FailedPayment;
import it.polimi.db2.entities.UserCustomer;
import it.polimi.db2.materializedViews.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class StatisticsService {
    @PersistenceContext( unitName = "databaseEJB" )
    private EntityManager em;


    public StatisticsService(){}

    public List<TotalPackageSales> getAllTotalPackageSales() {
        List<TotalPackageSales> totalPackageSales = em.createNamedQuery("TotalPackageSales.findAll", TotalPackageSales.class).getResultList();
        return totalPackageSales;
    }

    public List<TotalPurchasesPerPacket> getAllTotalTotalPurchasesPacket() {
        List<TotalPurchasesPerPacket> totalPurchasesPerPackets = em.createNamedQuery("TotalPurchasesPerPacket.findAll", TotalPurchasesPerPacket.class).getResultList();
        return totalPurchasesPerPackets;
    }

    public List<TotalPurchasesPerPacketValidityPeriod> getAllTotalPurchasesPacketValidityPeriod() {
        List<TotalPurchasesPerPacketValidityPeriod> totalPurchasesPerPacketValidityPeriods = em.createNamedQuery("TotalPurchasesPerPacketValidityPeriod.findAll", TotalPurchasesPerPacketValidityPeriod.class)
                .getResultList();
        return totalPurchasesPerPacketValidityPeriods;
    }

    public List<BestOptional> getAllBestOptionalProducts() {
        List<BestOptional> bestOptionals = em.createNamedQuery("BestOptional.findAllOrdered", BestOptional.class)
                .getResultList();
        return bestOptionals;
    }

    public List<AveragePackageOptionalProducts> getAllAveragePackageOptionalProducts() {
        List<AveragePackageOptionalProducts>  averagePackageOptionalProducts = em.createNamedQuery("AveragePackageOptionalProducts.findAll", AveragePackageOptionalProducts.class)
                .getResultList();
        return averagePackageOptionalProducts;
    }

    public List<UserCustomer> getInsolventUsers(){
        List<UserCustomer> users = em.createNamedQuery("UserCustomer.findAllInsovent", UserCustomer.class)
                .getResultList();
        return users;
    }

    public int userHasAlert(UserCustomer user){
        int alert = 0;
        List<AuditingTable> at = em.createNamedQuery("AuditingTable.findAuditingTableByUser", AuditingTable.class).setParameter("customer", user)
                .getResultList();
        if(at.size() != 0)
            alert = 1;

        return alert;
    }

    public List<FailedPayment> getUsersFailedPayment(UserCustomer userCustomer) {
        List<FailedPayment> failedPayments = em.createNamedQuery("FailedPayment.getFailedPaymentsOfUser", FailedPayment.class)
                    .setParameter("name", userCustomer)
                    .getResultList();
        return failedPayments;
    }
}
