package it.polimi.db2.entities;

import it.polimi.db2.entities.keys.PkgServiceInternetId;

import javax.persistence.*;
import java.io.Serializable;



@Entity
@IdClass(PkgServiceInternetId.class)
@Table(name = "PkgServiceInternet", schema = "database2")
@NamedQuery(name = "PkgServiceInternet.findServicePackageInternetService", query = "SELECT spo.internetService FROM PkgServiceInternet spo WHERE spo.packageService = :name")
public class PkgServiceInternet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "packageService")
    private String packageService;

    @Id
    @Column(name = "internetService")
    private String internetService;

    public PkgServiceInternet() {
    }


    public String getPackageService() { return packageService; }
    public void setPackageService(String name) {
        this.packageService = name;
    }
    public String getInternetService() {
        return internetService;
    }
    public void setInternetService(String internetService) {
        this.internetService = internetService;
    }
}
