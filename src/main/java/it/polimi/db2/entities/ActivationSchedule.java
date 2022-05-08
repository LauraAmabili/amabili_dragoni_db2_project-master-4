package it.polimi.db2.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "ActivationSchedule", schema = "database2")
@NamedQuery(name = "ActivationSchedule.getAllActivationSchedule", query= "SELECT act from ActivationSchedule act")
public class ActivationSchedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @OneToOne
    @JoinColumn(name = "orderId")
    private Orders order;

    @Column(name = "DateStart")
    private Date dateStart;

    @Column(name = "DateEnd")
    private Date dateEnd;



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

}
