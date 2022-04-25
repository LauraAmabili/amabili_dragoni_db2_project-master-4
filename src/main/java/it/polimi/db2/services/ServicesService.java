package it.polimi.db2.services;

import it.polimi.db2.entities.InternetService;
import it.polimi.db2.entities.MobilePhoneService;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ServicesService {

    @PersistenceContext( unitName = "databaseEJB" )
    private EntityManager em;

    public ServicesService() { }

   public void addNewInternetService(String name, int gigaNum, float extraFees, int fixedInternet){
        InternetService newIS = new InternetService();
        newIS.setName(name);
        newIS.setGigaNum(gigaNum);
        newIS.setExtraFees(extraFees);
        newIS.setFixedInternet(fixedInternet);
        em.persist(newIS);
    }

    public void addNewMobilePhoneService(String name, int minutesNum, int smsNum, float extraMinFee, float extraSmsFee){
        MobilePhoneService newMP = new MobilePhoneService();
        newMP.setName(name);
        newMP.setMinutesNum(minutesNum);
        newMP.setSmsNum(smsNum);
        newMP.setExtraMinFee(extraMinFee);
        newMP.setExtraSmsFee(extraSmsFee);
        em.persist(newMP);
    }
}
