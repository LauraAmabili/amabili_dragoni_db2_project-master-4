package it.polimi.db2.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "OptionalProduct", schema = "database2")
@NamedQuery( name = "OptionalProduct.findOptionalProductById",  query = "SELECT t FROM OptionalProduct t  WHERE t.name = ?1" )
@NamedQuery( name = "OptionalProduct.getAllOptionalProducts",  query = "SELECT t FROM OptionalProduct t" )

public class OptionalProduct {

    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "montlyFee")
    private float monthlyFee;

    @ManyToMany(mappedBy = "optionalOrdered")
    private Collection<Order> orders;



    public OptionalProduct() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public float getMonthlyFee() { return monthlyFee; }
    public void setMonthlyFee(float monthlyFee) { this.monthlyFee = monthlyFee; }
    public Collection<Order> getOrders() { return orders; }
    public void setOrders(Collection<Order> orders) { this.orders = orders; }
}
