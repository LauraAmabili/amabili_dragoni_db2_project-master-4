package it.polimi.db2.services;


import it.polimi.db2.entities.OptionalProduct;
import it.polimi.db2.entities.ServicePackage;
import it.polimi.db2.entities.ServicePackageOptional;
import it.polimi.db2.entities.UserCustomer;
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


        List<ServicePackage> sp;
        try {
            sp = em.createNamedQuery("ServicePackage.findServicePackageById", ServicePackage.class).setParameter("name",servicePackageName).getResultList();

        } catch (PersistenceException var5) {
            throw new CredentialsException("Packages Error");
        }
        return sp.get(0);
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


    public List<String> showOptionalProducts(ServicePackage servicePackage) throws CredentialsException, NonUniqueResultException {
        List<String> uList;
        try {
            uList = em.createNamedQuery("ServicePackageOptional.findServicePackageOptionalProducts", String.class).setParameter("name",servicePackage.getPackageName()).getResultList();
        } catch (PersistenceException var5) {
            throw new CredentialsException("Optional Products Error");
        }
        if (uList.isEmpty()) {
            return null;
        } else {
            return uList;
        }
    }

}
