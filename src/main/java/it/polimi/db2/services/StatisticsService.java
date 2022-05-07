package it.polimi.db2.services;

import it.polimi.db2.entities.AuditingTable;
import it.polimi.db2.entities.FailedPayment;
import it.polimi.db2.entities.UserCustomer;
import it.polimi.db2.entities.UserEmployee;
import it.polimi.db2.materializedViews.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;

@Stateless
public class StatisticsService {
    @PersistenceContext( unitName = "databaseEJB" )
    private EntityManager em;


    public StatisticsService(){}

    public List<TotalPackageSales> getAllTotalPackageSales() {
        List<TotalPackageSales> totalPackageSales = null;
        try {
            totalPackageSales = em.createNamedQuery("TotalPackageSales.findAll", TotalPackageSales.class).getResultList();
        } catch (PersistenceException e) {
            throw new PersistenceException("Cannot load number of purchases per package");
        }
        return totalPackageSales;
    }

    public List<TotalPurchasesPerPacket> getAllTotalTotalPurchasesPacket() {
        List<TotalPurchasesPerPacket> totalPurchasesPerPackets = null;
        try {
            totalPurchasesPerPackets = em.createNamedQuery("TotalPurchasesPerPacket.findAll", TotalPurchasesPerPacket.class).getResultList();
        } catch (PersistenceException e) {
            throw new PersistenceException("Cannot load Total Purchases Per Packets");
        }
        return totalPurchasesPerPackets;
    }

    public List<TotalPurchasesPerPacketValidityPeriod> getAllTotalPurchasesPacketValidityPeriod() {
        List<TotalPurchasesPerPacketValidityPeriod> totalPurchasesPerPacketValidityPeriods = null;
        try {
            totalPurchasesPerPacketValidityPeriods = em.createNamedQuery("TotalPurchasesPerPacketValidityPeriod.findAll", TotalPurchasesPerPacketValidityPeriod.class).getResultList();
        } catch (PersistenceException e) {
            throw new PersistenceException("Cannot load Total Purchases Per Packets");
        }
        return totalPurchasesPerPacketValidityPeriods;
    }

    public List<BestOptional> getAllBestOptionalProducts() {
        List<BestOptional> bestOptionals = null;
        try {
            bestOptionals = em.createNamedQuery("BestOptional.findAllOrdered", BestOptional.class).getResultList();
        } catch (PersistenceException e) {
            throw new PersistenceException("Cannot load Best Optional");
        }
        return bestOptionals;
    }

    public List<AveragePackageOptionalProducts> getAllAveragePackageOptionalProducts() {
        List<AveragePackageOptionalProducts> averagePackageOptionalProducts = null;
        try {
            averagePackageOptionalProducts = em.createNamedQuery("AveragePackageOptionalProducts.findAllOrdered", AveragePackageOptionalProducts.class).getResultList();
        } catch (PersistenceException e) {
            throw new PersistenceException("Cannot load Best Optional");
        }
        return averagePackageOptionalProducts;
    }

    public List<UserCustomer> getInsolventUsers(){
        List<UserCustomer> users = null;
        try {
            users = em.createNamedQuery("UserCustomer.findAllInsovent", UserCustomer.class).getResultList();
        } catch (PersistenceException e) {
            throw new PersistenceException("Cannot load Best Optional");
        }
        return users;
    }

    public int userHasAlert(UserCustomer user){
        int alert = 0;
        try {
            List<AuditingTable> at = em.createNamedQuery("AuditingTable.findAuditingTableByUser", AuditingTable.class).setParameter("username", user).getResultList();
            if(at.size() != 0) alert = 1;
        } catch (PersistenceException e) {
            throw new PersistenceException("Cannot load alerts");
        }
        return alert;
    }

    public List<FailedPayment> getUsersFailedPayment(UserCustomer userCustomer) {
        List<FailedPayment> failedPayments = null;
        try {
            failedPayments = em.createNamedQuery("FailedPayment.getFailedPaymentsOfUser", FailedPayment.class)
                    .setParameter("name", userCustomer)
                    .getResultList();
        } catch  (PersistenceException e) {
            throw new PersistenceException("Cannot load failed payments");
        }
        return failedPayments;
    }
}
