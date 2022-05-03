package it.polimi.db2.services;


import it.polimi.db2.entities.Order;
import it.polimi.db2.entities.ServicePackage;
import it.polimi.db2.entities.UserCustomer;
import it.polimi.db2.exceptions.CredentialsException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Stateless
public class OrderService {

    @PersistenceContext( unitName = "databaseEJB" )
    private EntityManager em;

    public OrderService() {

    }

    public List<Order> getAllOrders() throws CredentialsException, NonUniqueResultException {

        List<Order> uList = new ArrayList<>();

        try {
            uList = em.createNamedQuery("Orders.getAllOrders", Order.class).getResultList();

        } catch (PersistenceException var5) {
            throw new CredentialsException("Orders Error");
        }

        if (uList.isEmpty()) {
            return null;
        } else {
            return uList;
        }
    }

    public int getNumberOfSalesByServicePkgId (ServicePackage servicePkg) {
        List <Order> pkgServiceOrders= em.createNamedQuery("Order.getServicePkgOrders", Order.class).setParameter("servicePkgId", servicePkg).getResultList();
        int size = pkgServiceOrders.size();
        return size;
    }

    public void createOrder(int orderId, int validityPeriodMonth, int valid, Date dateStart, Date orderDateTime, float totalCost, UserCustomer userOrder) throws CredentialsException, NonUniqueResultException {

    }
}
