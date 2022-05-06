package it.polimi.db2.materializedViews;

import it.polimi.db2.entities.ServicePackage;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "TotalPurchasesPerPacket", schema = "database2")
@NamedQuery(name = "TotalPurchasesPerPacket.findAll", query = "SELECT tpp from TotalPurchasesPerPacket tpp")

public class TotalPurchasesPerPacket implements Serializable {
    @Id
    @OneToOne()
    @JoinColumn(name = "ServicePackage")
    private ServicePackage servicePackage;

    @Column(name = "purchases")
    private int purchases;

    public ServicePackage getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(ServicePackage servicePackage) {
        this.servicePackage = servicePackage;
    }

    public int getPurchases() {
        return purchases;
    }

    public void setPurchases(int purchases) {
        this.purchases = purchases;
    }
}
