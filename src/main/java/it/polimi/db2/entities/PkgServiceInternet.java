package it.polimi.db2.entities;

import javax.persistence.*;
import java.io.Serializable;



@Entity
@Table(name = "PkgServiceInternet", schema = "database2")
public class PkgServiceInternet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "packageService")
    private String packageService;


    @Column(name = "internetService")
    private String internetService;


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
