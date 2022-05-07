package it.polimi.db2.controllers;

import it.polimi.db2.entities.OptionalProduct;
import it.polimi.db2.entities.UserCustomer;
import it.polimi.db2.exceptions.CredentialsException;
import it.polimi.db2.services.UserCustomerService;
import org.apache.commons.lang.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ejb.EJB;
import javax.persistence.NonUniqueResultException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/CheckLoginCustomer")
public class CheckLoginCustomer extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private TemplateEngine templateEngine;

    @EJB(name = "it/polimi/db2/services/UserCustomerService")
    private UserCustomerService usrCustomerService;

    public CheckLoginCustomer(){
        super();
    }


    public void init() {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());

        // obtain and escape params
        String usrn = null;
        String pwd = null;
        try {
            usrn = StringEscapeUtils.escapeJava(request.getParameter("username"));
            pwd = StringEscapeUtils.escapeJava(request.getParameter("password"));

            if (usrn == null || pwd == null || usrn.isEmpty() || pwd.isEmpty()) {
                throw new Exception("Missing or empty credential value");
            }

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing credential value");
            return;
        }
        UserCustomer user;
        try {
            // query db to authenticate for user
            user = usrCustomerService.checkCredentials(usrn, pwd);
        } catch (CredentialsException | NonUniqueResultException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not check credentials");
            return;
        }
        // If the user exists, add info to the session and go to home page, otherwise
        // show login page with error message
        String path;
        if (user == null) {
            ctx.setVariable("loginErrorMsgCust", "Incorrect username or password");
            path = "/index.html";
            templateEngine.process(path, ctx, response.getWriter());
        } else {
            if(request.getSession(false) != null && request.getSession(false).getAttribute("servicePackageChosen")!=null){

                request.getSession(false).setAttribute("loggedCustomer", user);

                ctx.setVariable("startDate", request.getSession(false).getAttribute("startDate"));
                ctx.setVariable("chosenValidityPeriod", request.getSession(false).getAttribute("chosenValidityPeriod"));
                ctx.setVariable("servicePackageChosenCTX", request.getSession(false).getAttribute("servicePackageChosen"));
                ctx.setVariable("optionalProductsCTX", request.getSession(false).getAttribute("optionalProducts"));
                ctx.setVariable("totalCost", request.getSession(false).getAttribute("totalCost"));
                ctx.setVariable("loggedCustomer", user);
                templateEngine.process("/WEB-INF/ConfirmationPage.html", ctx, response.getWriter());

                return;
            }

            request.getSession().setAttribute("user", user);
            path = getServletContext().getContextPath() + "/home-page-customer";
            response.sendRedirect(path);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);

    }

}
