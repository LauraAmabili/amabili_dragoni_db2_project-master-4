package it.polimi.db2;


import javax.servlet.annotation.WebServlet;
import javax.ws.rs.Produces;

@WebServlet(name = "homePage", value = "/home-page")
public class HomePage {
    @Produces("text/plain")
    public String hello() {
        return "Customer correctly in ";
    }

}
