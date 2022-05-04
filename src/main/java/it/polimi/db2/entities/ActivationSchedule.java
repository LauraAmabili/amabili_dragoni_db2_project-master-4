package it.polimi.db2.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "ActivationSchedule", schema = "database2")
public class ActivationSchedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idActivationSchedule")
    private int idAvtivationSchedule;

    @Column(name = "DateStart")
    private Date dateStart;

    @Column(name = "DateEnd")
    private Date dateEnd;

    @Column(name = "orderId")
    private int orderId;

    public ActivationSchedule(int idAvtivationSchedule, Date dateStart, Date dateEnd, int orderId) {
        this.idAvtivationSchedule = idAvtivationSchedule;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.orderId = orderId;
    }

    public ActivationSchedule() {

    }

    public int getIdAvtivationSchedule() {
        return idAvtivationSchedule;
    }

    public void setIdAvtivationSchedule(int idAvtivationSchedule) {
        this.idAvtivationSchedule = idAvtivationSchedule;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
