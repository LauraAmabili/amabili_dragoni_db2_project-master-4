package it.polimi.db2.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@Entity
@Table(name = "MobilePhoneService", schema = "database2")
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
}
