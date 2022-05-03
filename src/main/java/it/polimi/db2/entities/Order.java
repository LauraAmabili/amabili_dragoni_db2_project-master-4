package it.polimi.db2.entities;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Order", schema = "database2")
@NamedQuery(name = "Orders.getAllOrders", query = "SELECT order from Order order")
@NamedQuery(name = "Order.getServicePkgOrders", query = "SELECT o from Order o where o.orderedService = :servicePkg")
@NamedQuery(name = "Order.getServicePkgValidityPeriodOrders", query = "SELECT o from Order o where o.orderedService = :servicePkg and o.validityPeriodMonth = :validityPeriod")
@NamedQuery(name = "Order.packagesWithoutOptionalProducts", query =
        "SELECT o FROM Order o LEFT JOIN OptionalOrdered oo ON o.orderId = oo.order " +
                "WHERE oo.optionalProduct IS NULL and o.orderedService = :servicePkg")

public class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "orderId")
    private int orderId;

    @Column(name = "ValidityPeriodMonth")
    private int validityPeriodMonth;

    @Column(name = "Valid")
    private int valid;

    @Column(name = "DateStart")
    private Date dateStart;

    @Column(name = "OrderDateTime")
    private java.util.Date orderDateTime;

    @Column(name = "TotalCost")
    private float totalCost;

    @ManyToOne
    @JoinColumn(name="userOrder", referencedColumnName="username")
    private UserCustomer userOrder;

    @ManyToOne
    @JoinColumn(name="orderedService", referencedColumnName="PackageName")
    private ServicePackage orderedService;


    public Order() {

    }


    public Order(int orderId, int validityPeriodMonth, int valid, Date dateStart, Date orderDateTime, float totalCost, UserCustomer userOrder) {
        this.orderId = orderId;
        this.validityPeriodMonth = validityPeriodMonth;
        this.valid = valid;
        this.dateStart = dateStart;
        this.orderDateTime = orderDateTime;
        this.totalCost = totalCost;
        this.userOrder = userOrder;
    }


    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getValidityPeriodMonth() {
        return validityPeriodMonth;
    }

    public void setValidityPeriodMonth(int validityPeriodMonth) {
        this.validityPeriodMonth = validityPeriodMonth;
    }



    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(Date orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public UserCustomer getUserOrder() {
        return userOrder;
    }

    public void setUserOrder(UserCustomer userOrder) {
        this.userOrder = userOrder;
    }

    public ServicePackage getOrderedService() {
        return orderedService;
    }

    public void setOrderedService(ServicePackage orderedService) {
        this.orderedService = orderedService;
    }
}
