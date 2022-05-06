package it.polimi.db2.materializedViews;

import it.polimi.db2.entities.ServicePackage;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "TotalPurchasesPerPacketValidityPeriod", schema = "database2")
@NamedQuery(name = "TotalPurchasesPerPacketValidityPeriod.findAll", query = "SELECT tppvp from TotalPurchasesPerPacketValidityPeriod tppvp")

public class TotalPurchasesPerPacketValidityPeriod implements Serializable {

    @Id
    @OneToOne()
    @JoinColumn(name = "ServicePackage")
    private ServicePackage servicePackage;

    @Column(name = "purchases12")
    private int purchases12;

    @Column(name = "purchases24")
    private int purchases24;

    @Column(name = "purchases36")
    private int purchases36;

    public ServicePackage getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(ServicePackage servicePackage) {
        this.servicePackage = servicePackage;
    }


    public int getPurchases12() {
        return purchases12;
    }

    public void setPurchases12(int purchases12) {
        this.purchases12 = purchases12;
    }

    public int getPurchases24() {
        return purchases24;
    }

    public void setPurchases24(int purchases24) {
        this.purchases24 = purchases24;
    }

    public int getPurchases36() {
        return purchases36;
    }

    public void setPurchases36(int purchases36) {
        this.purchases36 = purchases36;
    }
}
