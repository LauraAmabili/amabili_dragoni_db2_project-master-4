package it.polimi.db2.services;


import it.polimi.db2.entities.UserCustomer;
import it.polimi.db2.exceptions.CredentialsException;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

public class UserCustomerService {

    @PersistenceContext( unitName = "databaseEJB" )
    private EntityManager em;

    public UserCustomerService() {

    }
    public UserCustomer findBy(int customerId) {
        return em.find(UserCustomer.class, customerId);
    }

    public UserCustomer checkCredentials(String usrn, String pwd) throws CredentialsException, NonUniqueResultException {

        List<UserCustomer> uList = new ArrayList<>();

        try {
            uList = em.createNamedQuery("UserCustomer.checkCredentials", UserCustomer.class)
                    .setParameter(1, usrn)
                    .setParameter(2, pwd)
                    .getResultList();

        } catch (PersistenceException var5) {
            throw new CredentialsException("Could not verify credentials for customer");
        }

        if (uList.isEmpty()) {
            return null;
        } else if (uList.size() == 1) {
            return uList.get(0);
        } else {
            throw new NonUniqueResultException("More than one user registered with same credentials");
        }

    }

}
