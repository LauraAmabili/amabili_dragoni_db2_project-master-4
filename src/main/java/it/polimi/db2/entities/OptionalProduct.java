package it.polimi.db2.entities;

import javax.persistence.*;


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


    public OptionalProduct() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public float getMonthlyFee() { return monthlyFee; }
    public void setMonthlyFee(float monthlyFee) { this.monthlyFee = monthlyFee; }
}
