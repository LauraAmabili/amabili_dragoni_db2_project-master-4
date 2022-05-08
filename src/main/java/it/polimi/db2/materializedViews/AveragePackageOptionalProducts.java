package it.polimi.db2.materializedViews;


import it.polimi.db2.entities.OptionalProduct;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "AveragePackageOptionalProducts", schema = "database2")
@NamedQuery(name = "AveragePackageOptionalProducts.findAll", query = "SELECT apop from AveragePackageOptionalProducts apop ")


public class AveragePackageOptionalProducts implements Serializable {
    @Id
    @OneToOne()
    @JoinColumn(name = "servicePackage")
    private OptionalProduct servicePackage;

    @Column(name = "average")
    private float average;

    public OptionalProduct getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(OptionalProduct servicePackage) {
        this.servicePackage = servicePackage;
    }

    public float getAverage() {
        return average;
    }

    public void setAverage(float average) {
        this.average = average;
    }
}
