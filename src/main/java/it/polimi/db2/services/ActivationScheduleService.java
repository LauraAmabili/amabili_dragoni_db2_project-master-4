package it.polimi.db2.services;


import it.polimi.db2.entities.ActivationSchedule;

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

    public ActivationSchedule addNewActivationRecord(Date dateStart, Date dateEnd, int orderId){
        ActivationSchedule AS= new ActivationSchedule();
        AS.setDateStart(dateStart);
        AS.setOrderId(orderId);
        AS.setDateEnd(dateEnd);
        em.persist(AS);
        return AS;
    }
}
