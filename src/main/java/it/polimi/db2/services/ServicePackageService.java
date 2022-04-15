package it.polimi.db2.services;


import it.polimi.db2.entities.ServicePackage;
import it.polimi.db2.entities.UserCustomer;
import it.polimi.db2.exceptions.CredentialsException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;


@Stateless
public class ServicePackageService {

    @PersistenceContext( unitName = "databaseEJB" )
    private EntityManager em;

    public ServicePackageService() {

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

}
