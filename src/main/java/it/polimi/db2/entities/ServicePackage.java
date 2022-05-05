package it.polimi.db2.entities;


import javax.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "ServicePackage", schema = "database2")
@NamedQuery(name = "ServicePackage.showPackages", query = "SELECT sp from ServicePackage sp")
@NamedQuery(name = "ServicePackage.findServicePackageById", query = "SELECT sp FROM ServicePackage sp WHERE sp.packageName = :name")
public class ServicePackage implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "PackageName")
    private String packageName;

    @Column(name = "fixedPhoneNumber")
    private int fixedPhoneNumber;


    @ManyToOne
    @JoinColumn(name="packageFees", referencedColumnName="IdMontlyFee")
    private MonthlyFee packageFees;

    @ManyToMany
    @JoinTable(name = "PkgServicePhone",
            joinColumns = @JoinColumn(name = "servicePackage"),
            inverseJoinColumns = @JoinColumn(name = "mobilePhone"))
    private List<MobilePhoneService> mobilePhoneServices;

    @ManyToMany
    @JoinTable(name = "PkgServiceInternet",
            joinColumns = @JoinColumn(name = "packageService"),
            inverseJoinColumns = @JoinColumn(name = "internetService"))
    private List<InternetService> internetServices;

    @ManyToMany
    private List<OptionalProduct> optionalProducts;

    public ServicePackage() {

    }

   public ServicePackage(String PackageName, int fixedPhoneNumber, MonthlyFee packageFees){
       this.packageName = PackageName;
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
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getFixedPhoneNumber() {
        return fixedPhoneNumber;
    }

    public void setFixedPhoneNumber(int fixedPhoneNumber) {
        this.fixedPhoneNumber = fixedPhoneNumber;
    }


    public List<MobilePhoneService> getMobilePhoneServices() {
        return mobilePhoneServices;
    }

    public void setMobilePhoneServices(List<MobilePhoneService> mobilePhoneServices) {
        this.mobilePhoneServices = mobilePhoneServices;
    }

    public List<InternetService> getInternetServices() {
        return internetServices;
    }

    public void setInternetServices(List<InternetService> internetServices) {
        this.internetServices = internetServices;
    }

    public List<OptionalProduct> getOptionalProducts() {
        return optionalProducts;
    }

    public void setOptionalProducts(List<OptionalProduct> optionalProducts) {
        this.optionalProducts = optionalProducts;
    }
}
