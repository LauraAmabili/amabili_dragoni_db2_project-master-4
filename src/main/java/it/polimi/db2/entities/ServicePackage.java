package it.polimi.db2.entities;


import javax.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "ServicePackage", schema = "database2")
@NamedQuery(name = "ServicePackage.showPackages", query = "SELECT sp from ServicePackage sp")
@NamedQuery(name = "ServicePackage.findServicePackageById", query = "SELECT sp FROM ServicePackage sp WHERE sp.PackageName = :name")
public class ServicePackage implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "PackageName")
    private String PackageName;

   @Column(name = "fixedPhoneNumber")
   private int fixedPhoneNumber;

   // @Column(name = "packageFees")

    @ManyToOne
    @JoinColumn(name="packageFees", referencedColumnName="IdMontlyFee")
    private MonthlyFee packageFees;



    public ServicePackage() {

    }

   public ServicePackage(String PackageName, int fixedPhoneNumber, MonthlyFee packageFees){
       this.PackageName = PackageName;
       this.fixedPhoneNumber = fixedPhoneNumber;
       this.packageFees = packageFees;
   }

    public MonthlyFee getPackageFees() {
        return packageFees;
    }

    public void setPackageFees(MonthlyFee packageFees) {
        this.packageFees = packageFees;
    }

    public String getPackageName() {
        return PackageName;
    }

    public void setPackageName(String packageName) {
        PackageName = packageName;
    }

    public int getFixedPhoneNumber() {
        return fixedPhoneNumber;
    }

    public void setFixedPhoneNumber(int fixedPhoneNumber) {
        this.fixedPhoneNumber = fixedPhoneNumber;
    }


}
