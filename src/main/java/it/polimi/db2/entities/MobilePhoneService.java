package it.polimi.db2.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "MobilePhoneService", schema = "database2")
@NamedQuery( name = "MobilePhoneService.findMobilePhoneServiceById",  query = "SELECT t FROM MobilePhoneService t  WHERE t.name = ?1" )
@NamedQuery( name = "MobilePhoneService.getAllMobilePhoneServices",  query = "SELECT t FROM MobilePhoneService t" )

public class MobilePhoneService implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "minutesNum")
    private int minutesNum;

    @Column(name = "smsNum")
    private int smsNum;

    @Column(name = "extraMinFee")
    private float extraMinFee;

    @Column(name = "extraSmsFee")
    private float extraSmsFee;

    @ManyToMany(mappedBy = "mobilePhoneServices")
    private Collection<ServicePackage> servicePackages;

    public MobilePhoneService(String name, int minutesNum, int smsNum, float extraMinFee, float extraSmsFee) {
        this.name = name;
        this.minutesNum = minutesNum;
        this.smsNum = smsNum;
        this.extraMinFee = extraMinFee;
        this.extraSmsFee = extraSmsFee;
    }

    public MobilePhoneService() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinutesNum() {
        return minutesNum;
    }

    public void setMinutesNum(int minutesNum) {
        this.minutesNum = minutesNum;
    }

    public int getSmsNum() {
        return smsNum;
    }

    public void setSmsNum(int smsNum) {
        this.smsNum = smsNum;
    }

    public float getExtraMinFee() {
        return extraMinFee;
    }

    public void setExtraMinFee(float extraMinFee) {
        this.extraMinFee = extraMinFee;
    }

    public float getExtraSmsFee() {
        return extraSmsFee;
    }

    public void setExtraSmsFee(float extraSmsFee) {
        this.extraSmsFee = extraSmsFee;
    }


    public Collection<ServicePackage> getServicePackages() {
        return servicePackages;
    }

    public void setServicePackages(Collection<ServicePackage> servicePackages) {
        this.servicePackages = servicePackages;
    }
}
