package it.polimi.db2.services;


import it.polimi.db2.entities.ActivationSchedule;
import it.polimi.db2.entities.Orders;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

@Stateless
public class ActivationScheduleService {


    @PersistenceContext( unitName = "databaseEJB" )
    private EntityManager em;


    public ActivationScheduleService(){

    }

    public void addNewActivationRecord(Date dateStart, Date dateEnd, Orders order){
        ActivationSchedule AS= new ActivationSchedule();
        AS.setDateStart(dateStart);
        AS.setDateEnd(dateEnd);
        AS.setOrder(order);
        em.persist(AS);
    }
}
