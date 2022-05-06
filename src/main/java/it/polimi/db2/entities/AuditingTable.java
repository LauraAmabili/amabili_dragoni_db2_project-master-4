package it.polimi.db2.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "AuditingTable", schema = "database2")
@NamedQuery(name = "AuditingTable.findAuditingTableByUser", query= "SELECT at from AuditingTable at where at.username = :username")
public class AuditingTable  implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auditingTableId")
    private int auditingTableId;

    @Column(name = "email")
    private String email;

    @Column(name = "amount")
    private float amount;

    @Column(name = "dateTime")
    private Date date;

    @OneToOne
    @JoinColumn(name = "username")
    private UserCustomer username;


    public AuditingTable(int auditingTableId, String email, float amount, Date date, UserCustomer username) {
        this.auditingTableId = auditingTableId;
        this.email = email;
        this.amount = amount;
        this.date = date;
        this.username = username;
    }

    public AuditingTable() {

    }

    public int getAuditingTableId() {
        return auditingTableId;
    }

    public void setAuditingTableId(int auditingTableId) {
        this.auditingTableId = auditingTableId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public UserCustomer getUsername() {
        return username;
    }

    public void setUsername(UserCustomer username) {
        this.username = username;
    }
}
