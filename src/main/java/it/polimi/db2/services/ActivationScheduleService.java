package it.polimi.db2.services;


import it.polimi.db2.entities.ActivationSchedule;
import it.polimi.db2.entities.Order;

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

    public void addNewActivationRecord(Date dateStart, Date dateEnd, Order order){

        ActivationSchedule AS= new ActivationSchedule();
        Order o = order;
        AS.setDateStart(dateStart);
        AS.setDateEnd(dateEnd);
        AS.setOrder(order);
        em.persist(AS);
    }
}
