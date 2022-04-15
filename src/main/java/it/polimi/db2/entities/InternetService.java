package it.polimi.db2.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@Entity
@Table(name = "InternetService", schema = "database2")
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
    // TODO i think this should be boolean? non mi ricordo,  fare check


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
