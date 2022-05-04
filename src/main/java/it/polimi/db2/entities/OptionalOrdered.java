/*
package it.polimi.db2.entities;


import it.polimi.db2.entities.keys.OptionalOrderedId;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(OptionalOrderedId.class)
@Table(name = "OptionalOrdered", schema = "database2")
// query that returns the ordered optional product associated to a given order
@NamedQuery(name = "OptionalOrder.getOrderOptionalProducts", query = "SELECT o from OptionalOrdered o where o.order = :orderId")
// query that returns the optional products with the number of times they are selected ordered in discending order
@NamedQuery(name = "OptionalOrder.TopOptional", query = "SELECT o.optionalProduct as op, count(o.order) as tot from OptionalOrdered o group by o.optionalProduct order by count(o.order)")

public class OptionalOrdered implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "optionalProductId")
    private String optionalProduct;

    @Id
    @Column(name = "orderId")
    private int order;


    public OptionalOrdered() {
    }

    public String getOptionalProduct() {
        return optionalProduct;
    }

    public void setOptionalProduct(String optionalProduct) {
        this.optionalProduct = optionalProduct;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
*/
