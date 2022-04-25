package it.polimi.db2.entities;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Orders", schema = "database2")
@NamedQuery(name = "Orders.getAllOrders", query = "SELECT order from Orders order")
public class Orders implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "orderId")
    private int orderId;

    @Column(name = "ValidityPeriodMonth")
    private int ValidityPeriodMonth;

    @Column(name = "Valid")
    private int Valid;

    @Column(name = "DateStart")
    private Date DateStart;

    @Column(name = "OrderDateTime")
    private java.util.Date OrderDateTime;


    @Column(name = "TotalCost")
    private float TotalCost;


    @ManyToOne
    @JoinColumn(name="userOrder", referencedColumnName="username")
    private UserCustomer userOrder;



    @ManyToOne
    @JoinColumn(name="orderedService", referencedColumnName="PackageName")
    private ServicePackage orderedService;


    public Orders() {

    }


    public Orders(int orderId, int validityPeriodMonth, int valid, Date dateStart, Date orderDateTime, float totalCost, UserCustomer userOrder) {
        this.orderId = orderId;
        ValidityPeriodMonth = validityPeriodMonth;
        Valid = valid;
        DateStart = dateStart;
        OrderDateTime = orderDateTime;
        TotalCost = totalCost;
        this.userOrder = userOrder;
    }


    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getValidityPeriodMonth() {
        return ValidityPeriodMonth;
    }

    public void setValidityPeriodMonth(int validityPeriodMonth) {
        ValidityPeriodMonth = validityPeriodMonth;
    }

    public int getValid() {
        return Valid;
    }

    public void setValid(int valid) {
        Valid = valid;
    }

    public Date getDateStart() {
        return DateStart;
    }

    public void setDateStart(Date dateStart) {
        DateStart = dateStart;
    }

    public Date getOrderDateTime() {
        return OrderDateTime;
    }

    public void setOrderDateTime(Date orderDateTime) {
        OrderDateTime = orderDateTime;
    }

    public float getTotalCost() {
        return TotalCost;
    }

    public void setTotalCost(float totalCost) {
        TotalCost = totalCost;
    }

    public UserCustomer getUserOrder() {
        return userOrder;
    }

    public void setUserOrder(UserCustomer userOrder) {
        this.userOrder = userOrder;
    }
}
