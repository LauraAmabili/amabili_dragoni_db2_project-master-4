//package it.polimi.db2.entities;
//
//import it.polimi.db2.entities.keys.PkgServicePhoneId;
//
//import javax.persistence.*;
//import java.io.Serializable;
//
//
//
//@Entity
//@IdClass(PkgServicePhoneId.class)
//@Table(name = "PkgServicePhone", schema = "database2")
//@NamedQuery(name = "PkgServicePhone.findServicePackagePhoneService", query = "SELECT spo.mobilePhone FROM PkgServicePhone spo WHERE spo.servicePackage = :name")
//public class PkgServicePhone implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//    @Id
//    @Column(name = "servicePackage")
//    private String servicePackage;
//
//    @Id
//    @Column(name = "mobilePhone")
//    private String mobilePhone;
//
//    public PkgServicePhone() {
//    }
//
//    public String getServicePackage() {
//        return servicePackage;
//    }
//
//    public void setServicePackage(String name) {
//        this.servicePackage = name;
//    }
//
//    public String getMobilePhone() {
//        return mobilePhone;
//    }
//
//    public void setMobilePhone(String mobilePhone) {
//        this.mobilePhone = mobilePhone;
//    }
//}
