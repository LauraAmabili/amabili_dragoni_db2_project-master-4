package it.polimi.db2.entities;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "UserCustomer", schema = "database2")
@NamedQuery( name = "UserCustomer.checkCredentials",  query = "SELECT t FROM UserEmployee t  WHERE t.username = ?1 and t.password = ?2" )
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
    private int solvent = 1;

    public UserCustomer(){
    }

    public UserCustomer(String username, String password){
        this.username = username;
        this.password = password;
        this.email = email;
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

    public void setSolvent(int solvent) {
        this.solvent = solvent;
    }
}

