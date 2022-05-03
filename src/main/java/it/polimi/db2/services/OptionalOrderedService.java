package it.polimi.db2.services;

import it.polimi.db2.entities.OptionalOrdered;
import it.polimi.db2.entities.OptionalProduct;
import it.polimi.db2.entities.ServicePackageOptional;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class OptionalOrderedService {

    @PersistenceContext( unitName = "databaseEJB" )
    private EntityManager em;

    public OptionalOrderedService() { }


    public void addOptionalProductToOrder(String optionalProduct, int order) {
        OptionalOrdered optionalOrdered = new OptionalOrdered();
        optionalOrdered.setOptionalProduct(optionalProduct);
        optionalOrdered.setOrder(order);
        em.persist(optionalOrdered);
    }





}
