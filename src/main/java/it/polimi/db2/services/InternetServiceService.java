package it.polimi.db2.services;

import it.polimi.db2.entities.InternetService;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class InternetServiceService {

    @PersistenceContext( unitName = "databaseEJB" )
    private EntityManager em;

    public InternetServiceService(){}

    public Boolean internetServiceAlreadyExists(String name){
        List<InternetService> internetServices = em.createNamedQuery("InternetService.findInternetServiceById", InternetService.class).setParameter(1, name).getResultList();
        if (internetServices.isEmpty()) return true;
        else return false;
    }
}
