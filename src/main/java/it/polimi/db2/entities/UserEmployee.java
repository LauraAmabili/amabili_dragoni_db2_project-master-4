package it.polimi.db2.entities;

import javax.persistence.*;
import java.io.Serializable;


@Entity
// @Table( name = "UserEmployee", schema = "database2" )
@NamedQuery(
        name = "UserEmployee.checkCredentials",
        query = "SELECT t FROM UserEmployee t  WHERE t.username = ?1 and t.password = ?2"
)
public class UserEmployee implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "username", nullable = false, length = 45)
    private String username;

    @Column(name = "password", nullable = false, length = 45)
    private String password;


    public UserEmployee(){
    }

    public UserEmployee(String username, String password){
        this.username = username;
        this.password = password;
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

}

