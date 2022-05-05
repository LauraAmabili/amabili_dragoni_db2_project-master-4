package it.polimi.db2.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "InternetService", schema = "database2")
@NamedQuery( name = "InternetService.findInternetServiceById",  query = "SELECT t FROM InternetService t  WHERE t.name = ?1" )
@NamedQuery( name = "InternetService.getAllFixedInternetService",  query = "SELECT t FROM InternetService t  WHERE t.fixedInternet = 1" )
@NamedQuery( name = "InternetService.getAllMobileInternetService",  query = "SELECT t FROM InternetService t  WHERE t.fixedInternet = 0" )


public class InternetService implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "gigaNum")
    private int gigaNum;

    @Column(name = "extraFees")
    private float extraFees;

    @Column(name = "fixedInternet")
    private int fixedInternet;

    @ManyToMany(mappedBy = "internetServices")
    private List<ServicePackage> servicePackages;




    //constructors
    public InternetService(String name, int gigaNum, float extraFees, int fixedInternet) {
        this.name = name;
        this.gigaNum = gigaNum;
        this.extraFees = extraFees;
        this.fixedInternet = fixedInternet;
    }
    public InternetService() {}

    // getter and setter
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getGigaNum() {
        return gigaNum;
    }
    public void setGigaNum(int gigaNum) {
        this.gigaNum = gigaNum;
    }
    public float getExtraFees() {
        return extraFees;
    }
    public void setExtraFees(float extraFees) {
        this.extraFees = extraFees;
    }
    public int getFixedInternet() {
        return fixedInternet;
    }
    public void setFixedInternet(int fixedInternet) {
        this.fixedInternet = fixedInternet;
    }


}
