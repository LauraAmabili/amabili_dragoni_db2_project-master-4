package it.polimi.db2.services;

import it.polimi.db2.entities.InternetService;
import it.polimi.db2.entities.MonthlyFee;
import it.polimi.db2.entities.ServicePackage;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static java.lang.Integer.parseInt;

@Stateless
public class MonthlyFeesService {
    @PersistenceContext( unitName = "databaseEJB" )
    private EntityManager em;

    public MonthlyFeesService(){}


    public MonthlyFee addGetNewMonthlyFeeService (float price12, float price24, float price36){
        MonthlyFee mf = getMonthlyFee(price12, price24, price36);
        if(mf == null) {
            MonthlyFee newMf = new MonthlyFee();
            newMf.setTwelveMonthPrice(price12);
            newMf.setTwentyFourMonthPrice(price24);
            newMf.setThirtySixMonthPrice(price36);
            em.persist(newMf);
        }
        return getMonthlyFee(price12, price24, price36);

    }

    /**
     * the method returns null if there is no monthlyFee tuple with those parameters
     * */
    public MonthlyFee getMonthlyFee (float price12, float price24, float price36) {
        List<MonthlyFee> monthlyFees = em.createNamedQuery("MonthlyFee.findMonthlyFeeId", MonthlyFee.class)
                .setParameter("price12", price12)
                .setParameter("price24", price24)
                .setParameter("price36", price36)
                .getResultList();
        if(monthlyFees.isEmpty()) return null;
        return monthlyFees.get(0);

    }

}
