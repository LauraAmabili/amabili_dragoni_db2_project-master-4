package it.polimi.db2.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "ActivationSchedule", schema = "database2")
public class ActivationSchedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idActivationSchedule")
    private int idActivationSchedule;

    @Column(name = "DateStart")
    private Date dateStart;

    @Column(name = "DateEnd")
    private Date dateEnd;

    @OneToOne
    @JoinColumn(name = "orderId")
    private Orders order;

    public ActivationSchedule( Date dateStart, Date dateEnd, Orders order) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.order = order;
    }

    public ActivationSchedule() {
        super();
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

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public int getIdActivationSchedule() {
        return idActivationSchedule;
    }

    public void setIdActivationSchedule(int idActivationSchedule) {
        this.idActivationSchedule = idActivationSchedule;
    }
}
