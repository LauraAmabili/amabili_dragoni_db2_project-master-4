package it.polimi.db2.entities;


import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "Order", schema = "database2")
@NamedQuery(name = "Order.getAllOrders", query = "SELECT order from Order order")
// query that selects the orders of a given service package
@NamedQuery(name = "Order.getServicePkgOrders", query = "SELECT o from Order o where o.orderedService = :servicePkg")
// query that selects the orders with a given of a given service package
@NamedQuery(name = "Order.getServicePkgValidityPeriodOrders", query = "SELECT o from Order o where o.orderedService = :servicePkg and o.validityPeriodMonth = :validityPeriod")
// query that selects the orders of a given service package without optional products
@NamedQuery(name = "Order.getServicePkgOrdersWithoutOptionalProducts",
        query = "SELECT o from Order o where (o.orderedService = :servicePkg  and o.orderId not in (SELECT oo.order FROM OptionalOrdered oo))")
// query that selects the orders of a given service package with a given optional product
@NamedQuery(name = "Order.getServicePkgOrdersWithOptionalProducts",
        query = "SELECT o from Order o where (o.orderedService = :servicePkg  and o.orderId in (SELECT oo.order FROM OptionalOrdered oo WHERE oo.optionalProduct = :optionalProduct))")

// query that returns the order based on the customer and the date time of the creation
@NamedQuery(name = "Order.findOrderByDateTimeCustomer", query = "SELECT o from Order o where o.userOrder.username = :userOrderId and o.orderDateTime = :orderDT")
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderId")
    private int orderId;

    @Column(name = "ValidityPeriodMonth")
    private int validityPeriodMonth;

    @Column(name = "Valid")
    private int valid;

    //for start
    @Column(name = "DateStart")
    private Date dateStart;

    // for creation
    @Column(name = "OrderDateTime")
    private LocalDateTime orderDateTime;

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

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }
}
