package it.polimi.db2.services;


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
public class UserEmployeeService {


    @PersistenceContext( unitName = "databaseEJB" )
    private EntityManager em;

    public UserEmployeeService() {}

    public UserEmployee findUserById(UserEmployee emp) {
        String username = emp.getUsername();
        List<UserEmployee> usrs = em.createNamedQuery("UserEmployee.findUserById", UserEmployee.class).setParameter(1, username).getResultList();
        return usrs.get(0);
    }

    public UserEmployee checkCredentials(String usrn, String pwd) throws CredentialsException, NonUniqueResultException {
        List <UserEmployee>uList = new ArrayList<>();
        try {
            uList = em.createNamedQuery("UserEmployee.checkCredentials", UserEmployee.class)
                    .setParameter(1, usrn)
                    .setParameter(2, pwd)
                    .getResultList();
        } catch (PersistenceException var5) {
            throw new CredentialsException("Could not verify credentials for user");
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
