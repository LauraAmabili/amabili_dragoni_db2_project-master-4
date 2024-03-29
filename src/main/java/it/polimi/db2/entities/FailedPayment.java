package it.polimi.db2.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "FailedPayment", schema = "database2")
@NamedQuery(name = "FailedPayment.getFailedPaymentsOfUser", query = "SELECT payment from FailedPayment payment where payment.failedUser = :name")
@NamedQuery(name = "FailedPayment.getFailedPaymentByOrder", query = "SELECT payment from FailedPayment payment where payment.order = :order")
public class FailedPayment  implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idFailedPayment")
    private int idFailedPayment;

    @Column(name = "amount")
    private float  amount;

    @Column(name = "DateTime")
    private Date DateTime;

    @ManyToOne( cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "failedUser", referencedColumnName = "username")
    private UserCustomer failedUser;

    @ManyToOne
    @JoinColumn(name = "orderIdFailed")
    private Orders order;

    public FailedPayment(int idFailedPayment, float amount, Date dateTime, UserCustomer failedUser) {
        this.idFailedPayment = idFailedPayment;
        this.amount = amount;
        DateTime = dateTime;
        this.failedUser = failedUser;
    }

    public FailedPayment() {
    }

    public int getIdFailedPayment() {
        return idFailedPayment;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public void setIdFailedPayment(int idFailedPayment) {
        this.idFailedPayment = idFailedPayment;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Date getDateTime() {
        return DateTime;
    }

    public void setDateTime(Date dateTime) {
        DateTime = dateTime;
    }

    public UserCustomer getFailedUser() {
        return failedUser;
    }

    public void setFailedUser(UserCustomer failedUser) {
        this.failedUser = failedUser;
    }
}
