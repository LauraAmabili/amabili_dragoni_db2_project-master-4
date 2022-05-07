package it.polimi.db2.services;

import it.polimi.db2.entities.InternetService;
import it.polimi.db2.entities.MobilePhoneService;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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



    public Boolean internetServiceAlreadyExists(String name){
        List<InternetService> internetServices = null;
        em.createNamedQuery("InternetService.findInternetServiceById", InternetService.class).setParameter(1, name).getResultList();
        if (internetServices.isEmpty()) return false;
        else return true;
    }

    public InternetService getInternetServiceById(String name){
        List<InternetService> internetServices = em.createNamedQuery("InternetService.findInternetServiceById", InternetService.class).setParameter(1, name).getResultList();
        return internetServices.get(0);
    }
    public MobilePhoneService getMobilePhoneServiceById(String name){

        List<MobilePhoneService> mobilePhoneService = em.createNamedQuery("MobilePhoneService.findMobilePhoneServiceById", MobilePhoneService.class).setParameter(1, name).getResultList();
        return mobilePhoneService.get(0);
    }

    public Boolean mobilePhoneServiceAlreadyExists(String name){
        List<MobilePhoneService> mobilePhoneServices = em.createNamedQuery("MobilePhoneService.findMobilePhoneServiceById", MobilePhoneService.class).setParameter(1, name).getResultList();
        if (mobilePhoneServices.isEmpty()) return false;
        else return true;
    }

    public List<InternetService> getAllFixedInternetServices(){
        List<InternetService> fixedInternetServices = em.createNamedQuery("InternetService.getAllFixedInternetService", InternetService.class).getResultList();
        return fixedInternetServices;
    }

    public List<InternetService> getAllMobileInternetServices(){
        List<InternetService> mobileInternetServices = em.createNamedQuery("InternetService.getAllMobileInternetService", InternetService.class).getResultList();
        return mobileInternetServices;
    }

    public List<MobilePhoneService> getAllMobilePhoneServices(){
        List<MobilePhoneService> mobilePhoneServices = em.createNamedQuery("MobilePhoneService.getAllMobilePhoneServices").getResultList();
        return mobilePhoneServices;
    }

}
