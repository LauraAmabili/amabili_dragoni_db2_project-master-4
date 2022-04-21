package it.polimi.db2.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;



@Entity
@Table(name = "PkgServicePhone", schema = "database2")
public class PkgServicePhone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "servicePackage")
    private String name;

    @Column(name = "mobilePhone")
    private String mobilePhone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
}