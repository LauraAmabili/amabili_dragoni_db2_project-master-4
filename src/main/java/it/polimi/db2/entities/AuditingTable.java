package it.polimi.db2.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "AuditingTable", schema = "database2")
@NamedQuery(name = "AuditingTable.findAuditingTableByUser", query= "SELECT at from AuditingTable at where at.customer = :customer")
public class AuditingTable  implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="username")
    private String customerId;

    @OneToOne
    @PrimaryKeyJoinColumn(name = "username", referencedColumnName = "username")
    private UserCustomer customer;

    @Column (name = "email")
    private String email;

    @Column(name = "amount")
    private float amount;

    @Column(name = "dateTime")
    private Date date;


    public AuditingTable(int auditingTableId, String email, float amount, Date date, UserCustomer userCustomer) {
        this.customer = userCustomer;
        this.amount = amount;
        this.date = date;
        this.email = customer.getEmail();
    }

    public AuditingTable() {

    }



    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public UserCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(UserCustomer customer) {
        this.customer = customer;
        this.customerId = customer.getUsername();
        this.email = customer.getEmail();

    }
}
