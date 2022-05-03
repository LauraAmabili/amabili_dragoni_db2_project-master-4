package it.polimi.db2.entities;


import it.polimi.db2.entities.keys.OptionalOrderedId;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(OptionalOrderedId.class)
@Table(name = "OptionalOrdered", schema = "database2")
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
