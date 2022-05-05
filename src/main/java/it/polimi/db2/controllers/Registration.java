package it.polimi.db2.controllers;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.polimi.db2.entities.UserCustomer;
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
        if (request.getSession(false) == null) {
            userService.registerUser(email, usrn, pwd);


            // after creating an user the session is not created, it is required to log in
            ctx.setVariable("registrationMsg", "Registration completed, now log in");
            ctx.setVariable("username", "");
            ctx.setVariable("email", "");
            templateEngine.process("/index.html", ctx, response.getWriter());
            return;
        }



    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);



    }

}
