package it.polimi.db2.controllers;


import javax.ejb.EJB;
import javax.persistence.NonUniqueResultException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.polimi.db2.entities.*;
import it.polimi.db2.services.ActivationScheduleService;
import it.polimi.db2.services.OptionalProductService;
import it.polimi.db2.services.ServicesService;
import org.apache.commons.lang.StringEscapeUtils;

import it.polimi.db2.exceptions.CredentialsException;
import it.polimi.db2.services.UserEmployeeService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import java.io.IOException;
import java.util.List;

@WebServlet("/CheckLoginEmployee")
public class CheckLoginEmployee extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB(name = "it.polimi.db2.services/ServicesService")
    private ServicesService sService;

    @EJB(name = "it.polimi.db2.services/OptionalProductService")
    private OptionalProductService opService;

    private TemplateEngine templateEngine;


    @EJB(name = "it.polimi.db2.services/UserEmployeeService")
    private UserEmployeeService usrEmpService;

    @EJB(name = "services/ActivationScheduleService")
    private ActivationScheduleService asService;



    public CheckLoginEmployee(){
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
                throw new CredentialsException("Missing or empty credential value");
            }

        } catch (CredentialsException e) {
            ctx.setVariable("loginErrorMsg", e.getMessage());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing credential value");
            return;
        }
        UserEmployee user;
        try {
            // query db to authenticate for user
            user = usrEmpService.checkCredentials(usrn, pwd);
        } catch (CredentialsException | NonUniqueResultException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not check credentials");
            return;
        }

        // If the user exists, add info to the session and go to home page, otherwise
        // show login page with error message

        String path;
        if (user == null) {
            ctx.setVariable("loginErrorMsgEmp", "Incorrect username or password");
            path = "/index.html";
            templateEngine.process(path, ctx, response.getWriter());
        } else {
            List<InternetService> fixedInternetServices = sService.getAllFixedInternetServices();
            ctx.setVariable("fixedInternetServices", fixedInternetServices);
            List<InternetService> mobileInternetServices = sService.getAllMobileInternetServices();
            ctx.setVariable("mobileInternetServices", mobileInternetServices);
            List<MobilePhoneService> mobilePhoneServices = sService.getAllMobilePhoneServices();
            ctx.setVariable("mobilePhoneServices", mobilePhoneServices);
            List<OptionalProduct> optionalProducts = opService.getAllOptionalProducts();
            ctx.setVariable("optionalProducts", optionalProducts);
            ctx.setVariable("loggedEmp", user);
            request.getSession().setAttribute("employee", user);
            List<ActivationSchedule> listActivation = asService.getActivationSchedule();
            ctx.setVariable("listActivation", listActivation);
            templateEngine.process("/WEB-INF/HomePageEmployee.html", ctx, response.getWriter());
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);



    }
}
