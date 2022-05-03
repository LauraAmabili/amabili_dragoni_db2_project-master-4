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
import java.sql.ResultSet;
import java.sql.Statement;
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
            uList = em.createNamedQuery("Order.getAllOrders", Order.class).getResultList();

        } catch (PersistenceException var5) {
            throw new CredentialsException("Orders Error");
        }

        if (uList.isEmpty()) {
            return null;
        } else {
            return uList;
        }
    }

    public Order createOrder(int validityPeriodMonth, Date dateStart, Date orderDateTime, float totalCost, UserCustomer userOrder, ServicePackage servicePackage) throws CredentialsException, NonUniqueResultException {
        Order order = new Order();
        order.setValidityPeriodMonth(validityPeriodMonth);
        order.setDateStart(dateStart);
        order.setOrderDateTime(orderDateTime);
        order.setTotalCost(totalCost);
        order.setUserOrder(userOrder);
        order.setOrderedService(servicePackage);
        em.persist(order);
        return order;
    }

    public int getNumberOfSalesByServicePkg(ServicePackage sp){
        List <Order> orders = em.createNamedQuery("Order.getServicePkgOrders", Order.class).setParameter("servicePkg", sp).getResultList();
        return orders.size();
    }
    public int getNumberOfSalesByServicePkgValidityPeriod (ServicePackage sp, int validityPeriod){
        List <Order> orders = em.createNamedQuery("Order.getServicePkgValidityPeriodOrders", Order.class).
                setParameter("servicePkg", sp)
                .setParameter("validityPeriod", validityPeriod)
                .getResultList();
        return orders.size();
    }

    public int getNumOfOrderedWithoutOptionalProduct (ServicePackage sp){
        List<Order> ordersNoOp = em.createNamedQuery("Order.getServicePkgOrdersWithoutOptionalProducts", Order.class).setParameter("servicePkg", sp).getResultList();
        int ordersNoOpNum = ordersNoOp.size();
        return ordersNoOpNum;
    }

    public int getNumOfOrderedWithOptionalProduct (ServicePackage sp, String optionalProduct) {
        List<Order> ordersOp = em.createNamedQuery("Order.getServicePkgOrdersWithOptionalProducts", Order.class).setParameter("servicePkg", sp).setParameter("optionalProduct", optionalProduct).getResultList();
        return ordersOp.size();
    }



}
