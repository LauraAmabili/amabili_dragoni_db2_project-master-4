package it.polimi.db2.controllers;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.polimi.db2.entities.ServicePackage;
import it.polimi.db2.entities.UserCustomer;
import it.polimi.db2.exceptions.CredentialsException;
import it.polimi.db2.services.UserCustomerService;
import org.apache.commons.lang.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;


@WebServlet("/Registration")
public class Registration extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;
    @EJB(name = "services/UserCustomerService")
    private UserCustomerService userService;

    public Registration() {
        super();
    }

    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        String email = StringEscapeUtils.escapeJava(request.getParameter("email"));
        String usrn = StringEscapeUtils.escapeJava(request.getParameter("username"));
        String pwd = StringEscapeUtils.escapeJava(request.getParameter("password"));

        WebContext ctx = new WebContext(request, response, this.getServletContext(), request.getLocale());

        if (usrn != null && !usrn.equals(""))
            ctx.setVariable("username", usrn);
        if (email != null && !email.equals(""))
            ctx.setVariable("email", usrn);

        // session not exist - user is in index.html
        //rcaso in cui ho già creato sessione con eomployee-> entro perchè non c'è un loggedCustomer
        // caso in cui logged customer non c'è e non c'è neanche la sessione
        if (((request.getSession(false ) == null) || (request.getSession(false).getAttribute("loggedCustomer") == null) && email != null)) {
            boolean userNotAlreadyExists = true;
            try {
                userNotAlreadyExists = userService.registerUser(email, usrn, pwd);
            } catch (CredentialsException e) {
                e.printStackTrace();
            }

            // registerUser ritorna false se l'utente esiste
            //register user ritorna true se non esiste
            ctx.setVariable("registrationMsg", "Registration completed, now log in");
            ctx.setVariable("userNotAlreadyExists", userNotAlreadyExists);
            ctx.setVariable("userAlreadyExistsMessage", "User already exists, please log in!");

            ctx.setVariable("username", "");
            ctx.setVariable("email", "");
            templateEngine.process("/index.html", ctx, response.getWriter());
            return;
        }

        if(request.getParameter("servicePackageChosenCTX") != null) {

            request.getSession(false).setAttribute("chosenValidityPeriod", request.getSession(false).getAttribute("chosenValidityPeriod"));
            request.getSession(false).setAttribute("servicePackageChosen",  request.getSession(false).getAttribute("servicePackageChosenCTX"));
            request.getSession(false).setAttribute("optionalProducts", request.getSession(false).getAttribute("optionalProducts"));
            request.getSession(false).setAttribute("totalCost", request.getSession(false).getAttribute("totalCost"));
            request.getSession(false).setAttribute("startDate", request.getSession(false).getAttribute("startDate"));

        }


        templateEngine.process("/index.html", ctx, response.getWriter());




    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);



    }

}
