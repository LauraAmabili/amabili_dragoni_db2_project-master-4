package it.polimi.db2.controllers;


import it.polimi.db2.entities.ServicePackage;
import it.polimi.db2.exceptions.CredentialsException;
import it.polimi.db2.services.ServicePackageService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Produces;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.util.List;

import static java.lang.System.out;

@WebServlet(name = "homePage", value = "/home-page")
public class HomePage extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;
    @EJB(name = "services/ServicePackageService")
    private ServicePackageService sps;

    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String result = "Login worked for Customer";
        String result2 = "Page with packages";
        List<ServicePackage> sp = null;
        try {
            sp = sps.showPackages();
        } catch (CredentialsException e) {
            e.printStackTrace();
        }

        final WebContext ctx = new WebContext(req, resp, this.getServletContext(), req.getLocale());
        //PrintWriter out = resp.getWriter();
        // out.print(sp);
        //out.println(result);
        // out.println(result2);


        ctx.setVariable("packageList", sp);
        // variabile in HTML di nome packageList perchè sto assegnano alla variabile sp
        templateEngine.process("/WEB-INF/HomePage.html", ctx, resp.getWriter());


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
        
  

    }
}
