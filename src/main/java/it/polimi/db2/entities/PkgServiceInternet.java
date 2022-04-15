package it.polimi.db2.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;



@Entity
@Table(name = "PkgServiceInternet", schema = "database2")
public class PkgServiceInternet implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @Column(name = "packageService")
    private String name;

    @Column(name = "internetService")
    private String internetService;


    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInternetService() {
        return internetService;
    }

    public void setInternetService(String internetService) {
        this.internetService = internetService;
    }
}
