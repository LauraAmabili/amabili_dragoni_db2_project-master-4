package it.polimi.db2.materializedViews;

import it.polimi.db2.entities.ServicePackage;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "TotalPackageSales", schema = "database2")
@NamedQuery(name = "TotalPackageSales.findAll", query = "SELECT tps from TotalPackageSales tps")

public class TotalPackageSales implements Serializable {
    @Id
    @OneToOne()
    @JoinColumn(name = "ServicePackage")
    private ServicePackage servicePackage;

    @Column(name = "totSalesWithOp")
    private float totSalesWithOp;

    @Column(name = "totSalesWithoutOp")
    private float totSalesWithoutOp;


    public ServicePackage getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(ServicePackage servicePackage) {
        this.servicePackage = servicePackage;
    }

    public float getTotSalesWithOp() {
        return totSalesWithOp;
    }

    public void setTotSalesWithOp(float totSalesWithOp) {
        this.totSalesWithOp = totSalesWithOp;
    }

    public float getTotSalesWithoutOp() {
        return totSalesWithoutOp;
    }

    public void setTotSalesWithoutOp(float totSalesWithoutOp) {
        this.totSalesWithoutOp = totSalesWithoutOp;
    }

}
