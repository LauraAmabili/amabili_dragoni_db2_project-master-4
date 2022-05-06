package it.polimi.db2.services;


import it.polimi.db2.entities.OptionalProduct;
import it.polimi.db2.entities.Orders;
import it.polimi.db2.entities.ServicePackage;
import it.polimi.db2.entities.UserCustomer;
import it.polimi.db2.exceptions.CredentialsException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Stateless
public class OrderService {

    @PersistenceContext( unitName = "databaseEJB" )
    private EntityManager em;

    public OrderService() {

    }

    public List<Orders> getAllOrders() throws CredentialsException, NonUniqueResultException {

        List<Orders> uList = new ArrayList<>();

        try {
            uList = em.createNamedQuery("Order.getAllOrders", Orders.class).getResultList();

        } catch (PersistenceException var5) {
            throw new CredentialsException("Orders Error");
        }

        if (uList.isEmpty()) {
            return null;
        } else {
            return uList;
        }
    }

    public Orders createOrder(int validityPeriodMonth, Date dateStart, float totalCost, UserCustomer userOrder, ServicePackage servicePackage, List<OptionalProduct> optionalProducts) throws CredentialsException, NonUniqueResultException {
        Orders order = new Orders();
        order.setValidityPeriodMonth(validityPeriodMonth);
        order.setDateStart(dateStart);
        order.setValid(1);
        order.setTotalCost(totalCost);
        order.setUserOrder(userOrder);
        order.setOrderedService(servicePackage);
        LocalDateTime orderDateTime = LocalDateTime.now();
        order.setOrderDateTime(orderDateTime);
        if(optionalProducts!=null) order.setOptionalOrdered(optionalProducts);
        em.persist(order);
        em.flush();
        return order;

    }

    public int getNumberOfSalesByServicePkg(ServicePackage sp){
        List <Orders> orders = em.createNamedQuery("Order.getServicePkgOrders", Orders.class).setParameter("servicePkg", sp).getResultList();
        return orders.size();
    }
    public int getNumberOfSalesByServicePkgValidityPeriod (ServicePackage sp, int validityPeriod){
        List <Orders> orders = em.createNamedQuery("Order.getServicePkgValidityPeriodOrders", Orders.class).
                setParameter("servicePkg", sp)
                .setParameter("validityPeriod", validityPeriod)
                .getResultList();
        return orders.size();
    }

    public int getNumOfOrderedWithoutOptionalProduct (ServicePackage sp){
        List<Orders> ordersNoOp = em.createNamedQuery("Order.getServicePkgOrdersWithoutOptionalProducts", Orders.class).setParameter("servicePkg", sp).getResultList();
        int ordersNoOpNum = ordersNoOp.size();
        return ordersNoOpNum;
    }

    public int getNumOfOrderedWithOptionalProduct (ServicePackage sp, String optionalProduct) {
        List<Orders> ordersOp = em.createNamedQuery("Order.getServicePkgOrdersWithOptionalProducts", Orders.class).setParameter("servicePkg", sp).setParameter("optionalProduct", optionalProduct).getResultList();
        return ordersOp.size();
    }

    public List<Orders> getServicePackageOrders (ServicePackage sp) {
        List <Orders> orders = em.createNamedQuery("Order.getServicePkgOrders", Orders.class).setParameter("servicePkg", sp).getResultList();
        return orders;
    }

    public Orders findOrderByDateTimeCustomer(String customer, LocalDateTime dateTime){
        List<Orders> order = em.createNamedQuery("Order.findOrderByDateTimeCustomer", Orders.class)
                .setParameter("userOrderId", customer)
                .setParameter("orderDT", dateTime)
                .getResultList();
        List<Orders> o2 = order;
        return order.get(0);
    }

    public List<Orders> getNotValidOrdersOfUser (UserCustomer us) {
        List <Orders> orders = em.createNamedQuery("Order.getNotValidOrdersOfUser", Orders.class).setParameter("userCustomer", us).getResultList();
        return orders;
    }

    public Orders getOrder(int orderId){

        Orders order = em.find(Orders.class, orderId);
        return order;

    }

    public void setValid(Orders order, int valid){
        Orders order2 = em.find(Orders.class, order.getOrderId());
        order2.setValid(valid);
    }
}
