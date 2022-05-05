package it.polimi.db2.services;


import it.polimi.db2.entities.*;
import it.polimi.db2.exceptions.CredentialsException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.security.Provider;
import java.util.ArrayList;
import java.util.List;


@Stateless
public class ServicePackageService {

    @PersistenceContext( unitName = "databaseEJB" )
    private EntityManager em;

    public ServicePackageService() { }

    public ServicePackage findServicePackageById(String servicePackageName) throws CredentialsException {
        ServicePackage sp = em.find(ServicePackage.class, servicePackageName);
        return sp;
      /*  List<ServicePackage> sp;
        try {
            sp = em.createNamedQuery("ServicePackage.findServicePackageById", ServicePackage.class).setParameter("name",servicePackageName).getResultList();

        } catch (PersistenceException var5) {
            throw new CredentialsException("Packages Error");
        }
        return sp.get(0);*/
    }

    public List<ServicePackage> showPackages() throws CredentialsException, NonUniqueResultException {
        List<ServicePackage> uList = new ArrayList<>();
        try {
            uList = em.createNamedQuery("ServicePackage.showPackages", ServicePackage.class).getResultList();
        } catch (PersistenceException var5) {
            throw new CredentialsException("Packages Error");
        }
        if (uList.isEmpty()) {
            return null;
        } else {
            return uList;
        }
    }


    public List<String> showInternetServices(ServicePackage servicePackage) throws CredentialsException, NonUniqueResultException {
//        List<String> uList;
//        try {
//            uList = em.createNamedQuery("PkgServiceInternet.findServicePackageInternetService", String.class).setParameter("name",servicePackage.getPackageName()).getResultList();
//        } catch (PersistenceException var5) {
//            throw new CredentialsException("Optional Products Error");
//        }
//        if (uList.isEmpty()) {
//            return null;
//        } else {
//            return uList;
//        }
        List<String> isIds = new ArrayList<>();
        List<InternetService> internetServices = servicePackage.getInternetServices();
        if(internetServices != null) {
            internetServices.forEach(isService -> isIds.add(isService.getName()));
        }
        return isIds;
    }

    public List<String> showMobilePhoneServices(ServicePackage servicePackage) throws CredentialsException, NonUniqueResultException {
//        List<String> uList;
//        try {
//            uList = em.createNamedQuery("PkgServicePhone.findServicePackagePhoneService", String.class).setParameter("name",servicePackage.getPackageName()).getResultList();
//        } catch (PersistenceException var5) {
//            throw new CredentialsException("Optional Products Error");
//        }
//        if (uList.isEmpty()) {
//            return null;
//        } else {
//            return uList;
//        }
        List<String> mpsIds = new ArrayList<>();
        List<MobilePhoneService> mobilePhoneServices = servicePackage.getMobilePhoneServices();
        if(mobilePhoneServices != null) {
            mobilePhoneServices.forEach(mpService -> mpsIds.add(mpService.getName()));
        }
        return mpsIds;
    }
    public List<String> showOptionalProducts(ServicePackage servicePackage) throws CredentialsException, NonUniqueResultException {
//        List<String> uList;
//        try {
//            uList = em.createNamedQuery("ServicePackageOptional.findServicePackageOptionalProducts", String.class).setParameter("name",servicePackage.getPackageName()).getResultList();
//        } catch (PersistenceException var5) {
//            throw new CredentialsException("Optional Products Error");
//        }
//        if (uList.isEmpty()) {
//            return null;
//        } else {
//            return uList;
//        }
        List<String> opsIds = new ArrayList<>();
        List <OptionalProduct> optionalProducts = servicePackage.getOptionalProducts();
        if(optionalProducts != null) {
            optionalProducts.forEach(opService -> opsIds.add(opService.getName()));
        }
        return opsIds;
    }

    public Boolean servicePackageAlreadyExists(String pkgName){
        List<ServicePackage> servicePackages = em.createNamedQuery("ServicePackage.findServicePackageById", ServicePackage.class).setParameter("name", pkgName ).getResultList();
        return !servicePackages.isEmpty();
    }

    public void addNewServicePackage (String name, int fixedPhone, MonthlyFee packageFee, List<InternetService> internetServices, List<MobilePhoneService> mobilePhoneServices, List<OptionalProduct> optionalProducts) {
        ServicePackage newSP = new ServicePackage(name, fixedPhone, packageFee, mobilePhoneServices, internetServices, optionalProducts);
//        newSP.setPackageName(name);
//        newSP.setFixedPhoneNumber(fixedPhone);
//        newSP.setPackageFees(packageFee);
//        if (internetServices != null) newSP.setInternetServices(internetServices);
//        if (mobilePhoneServices != null) newSP.setMobilePhoneServices(mobilePhoneServices);
//        if (optionalProducts != null) newSP.setOptionalProducts(optionalProducts);

        try {
            em.persist(newSP);
            em.flush();
        } catch (PersistenceException e) {
            throw new PersistenceException(
                    "An error has occurred during the service package creation, please retry later");
        }
    }

    public float costPerMonth(int validityPeriod, ServicePackage sp){
        float pricePerMonth = 0;
        float total = 0;
        if(validityPeriod == 12){
            pricePerMonth = sp.getPackageFees().getTwelveMonthPrice();
        } else if(validityPeriod == 24){
            pricePerMonth = sp.getPackageFees().getTwentyFourMonthPrice();
        } else if(validityPeriod == 36){
            pricePerMonth = sp.getPackageFees().getThirtySixMonthPrice();
        }
        total = pricePerMonth* validityPeriod;

        return total;

    }

}
