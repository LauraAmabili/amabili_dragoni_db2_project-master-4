package it.polimi.db2.controllers;

import it.polimi.db2.entities.*;
import it.polimi.db2.services.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.polimi.db2.exceptions.CredentialsException;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static java.lang.System.out;

//@WebServlet(name = "homePageEmployee", value = "/home-page-employee")
@WebServlet("/home-page-employee")
public class HomePageEmployee extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;
    @EJB(name = "services/ServicePackageService")
    private ServicePackageService sps;
    @EJB(name = "services/UserEmployeeService")
    private UserEmployeeService usrEmpService;
    @EJB(name = "it.polimi.db2.services/ServicesService")
    private ServicesService sService;
    @EJB(name = "it.polimi.db2.services/OptionalProductService")
    private OptionalProductService opService;
    @EJB(name = "services/ActivationScheduleService")
    private ActivationScheduleService asService;

    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ServicePackage> sp = null;
        UserEmployee employee = null;
        try {
            sp = sps.showPackages();
        } catch (CredentialsException e) {
            e.printStackTrace();
        }

        final WebContext ctx = new WebContext(req, resp, this.getServletContext(), req.getLocale());

        if(req.getSession(false)!=null  &&  req.getSession(false).getAttribute("employee")!=null) {
            //update of object user to make sure is the current one
            employee = usrEmpService.findUserById((UserEmployee) req.getSession().getAttribute("employee"));
            req.getSession(false).setAttribute("employee", employee);

            List<InternetService> fixedInternetServices = sService.getAllFixedInternetServices();
            ctx.setVariable("fixedInternetServices", fixedInternetServices);
            List<InternetService> mobileInternetServices = sService.getAllMobileInternetServices();
            ctx.setVariable("mobileInternetServices", mobileInternetServices);
            List<MobilePhoneService> mobilePhoneServices = sService.getAllMobilePhoneServices();
            ctx.setVariable("mobilePhoneServices", mobilePhoneServices);
            List<OptionalProduct> optionalProducts = opService.getAllOptionalProducts();
            ctx.setVariable("optionalProducts", optionalProducts);
            ctx.setVariable("loggedEmp", employee);
            List<ActivationSchedule> listActivation = asService.getActivationSchedule();
            ctx.setVariable("listActivation", listActivation);
            templateEngine.process("/WEB-INF/HomePageEmployee.html", ctx, resp.getWriter());

        }



    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);



    }
}
