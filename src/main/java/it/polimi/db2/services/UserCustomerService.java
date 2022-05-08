package it.polimi.db2.services;


import it.polimi.db2.entities.UserCustomer;
import it.polimi.db2.entities.UserEmployee;
import it.polimi.db2.exceptions.CredentialsException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;


@Stateless
public class UserCustomerService {

    @PersistenceContext( unitName = "databaseEJB" )
    private EntityManager em;

    public UserCustomerService() {

    }
    public UserCustomer findCustomerById(UserCustomer customer) {
        String username = customer.getUsername();
        List<UserCustomer> usrs = em.createNamedQuery("UserCustomer.findCustomerById", UserCustomer.class).setParameter(1, username).getResultList();
        return usrs.get(0);
    }

    public boolean findCustomerByName(String username) {
        List<UserCustomer> usrs = em.createNamedQuery("UserCustomer.findCustomerById", UserCustomer.class).setParameter(1, username).getResultList();
        if(usrs.isEmpty()){
            return true;
        }
        return false;
    }

    public boolean findCustomerByEmail(String email) {
        List<UserCustomer> usrs = em.createNamedQuery("UserCustomer.findCustomerByMail", UserCustomer.class).setParameter(1, email).getResultList();
        if(usrs.isEmpty()){
            return true;
        }
        return false;
    }

    public List<UserCustomer> getAllCustomers() throws CredentialsException {
        List<UserCustomer> users = em.createNamedQuery("UserCustomer.findAllCustomers", UserCustomer.class).getResultList();
        return users;
    }

    public boolean registerUser(String email, String username, String password) throws CredentialsException {

        if(!findCustomerByName(username) || !findCustomerByEmail(email)){
            return false;
        } else {
            UserCustomer user = new UserCustomer();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);
            user.setSolvent(1);
            em.persist(user);
            return true;
        }
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

    public UserCustomer setInsolvent(UserCustomer user, int solvent){
        UserCustomer u = em.find(UserCustomer.class, user.getUsername());
        u.setSolvent(solvent);
        return u;
    }

}
