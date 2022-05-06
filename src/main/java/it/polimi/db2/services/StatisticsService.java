package it.polimi.db2.services;

import it.polimi.db2.materializedViews.BestOptional;
import it.polimi.db2.materializedViews.TotalPackageSales;
import it.polimi.db2.materializedViews.TotalPurchasesPerPacket;
import it.polimi.db2.materializedViews.TotalPurchasesPerPacketValidityPeriod;

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

}
