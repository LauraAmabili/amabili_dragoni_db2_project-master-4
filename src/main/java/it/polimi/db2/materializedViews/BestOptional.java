package it.polimi.db2.materializedViews;


import it.polimi.db2.entities.OptionalProduct;
import it.polimi.db2.entities.ServicePackage;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "BestOptional", schema = "database2")
@NamedQuery(name = "BestOptional.findAllOrdered", query = "SELECT bo from BestOptional bo ORDER BY bo.totSales DESC ")

public class BestOptional implements Serializable {

    @Id
    @OneToOne()
    @JoinColumn(name = "optionalProduct")
    private OptionalProduct optionalProduct;

    @Column(name = "pricePerMonth")
    private float pricePerMonth;

    @Column(name = "totSales")
    private float totSales;

    public OptionalProduct getOptionalProduct() {
        return optionalProduct;
    }

    public void setOptionalProduct(OptionalProduct optionalProduct) {
        this.optionalProduct = optionalProduct;
    }


    public float getPricePerMonth() {
        return pricePerMonth;
    }

    public void setPricePerMonth(float pricePerMonth) {
        this.pricePerMonth = pricePerMonth;
    }

    public float getTotSales() {
        return totSales;
    }

    public void setTotSales(float totSales) {
        this.totSales = totSales;
    }
}
