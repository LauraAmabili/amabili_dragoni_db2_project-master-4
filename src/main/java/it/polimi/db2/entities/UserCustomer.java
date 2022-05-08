package it.polimi.db2.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;


@Entity
@Table(name = "UserCustomer", schema = "database2")
@NamedQuery( name = "UserCustomer.checkCredentials",  query = "SELECT t FROM UserCustomer t  WHERE t.username = ?1 and t.password = ?2" )
@NamedQuery( name = "UserCustomer.findCustomerById",  query = "SELECT t FROM UserCustomer t  WHERE t.username = ?1" )
@NamedQuery(name = "UserCustomer.findAllInsovent", query = "SELECT c FROM UserCustomer as c WHERE c.solvent = 0")
@NamedQuery( name = "UserCustomer.findAllCustomers",  query = "SELECT t FROM UserCustomer t" )

public class UserCustomer implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "solvent")
    private int solvent;

    @OneToMany(mappedBy = "failedUser")
    private List<FailedPayment> failedPaymentList;

    @OneToMany(mappedBy = "userOrder", cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private Collection<Orders> orders;

    @OneToOne(mappedBy = "customer")
    private AuditingTable auditingTable;



    public UserCustomer(){
    }

    public UserCustomer(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
        this.solvent = 1;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSolvent() {
        return solvent;
    }

    public List<FailedPayment> getFailedPaymentList() {
        return failedPaymentList;
    }

    public void setFailedPaymentList(List<FailedPayment> failedPaymentList) {
        this.failedPaymentList = failedPaymentList;
    }

    public Collection<Orders> getOrders() {
        return orders;
    }

    public void setOrders(Collection<Orders> orders) {
        this.orders = orders;
    }

    public AuditingTable getAuditingTable() {
        return auditingTable;
    }

    public void setAuditingTable(AuditingTable auditingTable) {
        this.auditingTable = auditingTable;
    }

    public void setSolvent(int solvent) {
        this.solvent = solvent;
    }
}

