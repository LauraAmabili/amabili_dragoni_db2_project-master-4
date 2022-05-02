package it.polimi.db2.services;


import it.polimi.db2.entities.MontlyFee;
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
public class ValidityPeriodService {

    @PersistenceContext( unitName = "databaseEJB" )
    private EntityManager em;

    public ValidityPeriodService() { }

    public void findMontlyFee(String servicePackageName) throws CredentialsException {


    }



}
