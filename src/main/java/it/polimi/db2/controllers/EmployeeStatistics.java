package it.polimi.db2.controllers;

import it.polimi.db2.entities.ServicePackage;
import it.polimi.db2.entities.UserEmployee;
import it.polimi.db2.exceptions.CredentialsException;
import it.polimi.db2.services.OrderService;
import it.polimi.db2.services.ServicePackageService;
import it.polimi.db2.services.UserCustomerService;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebServlet("/go-to-statistics")
public class EmployeeStatistics extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;


    @EJB(name = "it/polimi/db2/services/ServicePackageService")
    private ServicePackageService spService;

    @EJB(name = "it/polimi/db2/services/OrderService")
    private OrderService orderService;



    public EmployeeStatistics() {
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());

        // take the username from the session and set it for the new session
        UserEmployee employee = (UserEmployee) request.getSession(false).getAttribute("employee");
        request.getSession(false).setAttribute("employee", employee);
        ctx.setVariable("loggedEmp", employee);

        // compute the number of sales for each service package
        HashMap<String, Integer> salesPerPackage = new HashMap<String, Integer>();
        try {
            List<ServicePackage> servicePackages = spService.showPackages();
            servicePackages.forEach(
                    sp -> salesPerPackage.put(sp.getPackageName(),
                    orderService.getNumberOfSalesByServicePkg(sp)));
        } catch (CredentialsException e) {
            e.printStackTrace();
        }

        // compute the number of sales for each service package for each monthly fee
        HashMap<String, List<Integer>> salesPkgValidityPeriod = new HashMap<String, List<Integer>>();
        try {
            List<ServicePackage> servicePackages = spService.showPackages();
            servicePackages.forEach(sp -> {
                int sales12 = orderService.getNumberOfSalesByServicePkgValidityPeriod(sp, 12);
                int sales24 = orderService.getNumberOfSalesByServicePkgValidityPeriod(sp, 24);
                int sales36 = orderService.getNumberOfSalesByServicePkgValidityPeriod(sp, 36);
                List<Integer> sales = new ArrayList<Integer>();
                sales.add(sales12); sales.add(sales24); sales.add(sales36);

                salesPkgValidityPeriod.put(sp.getPackageName(), sales);
            });
        } catch (CredentialsException e) {
            e.printStackTrace();
        }

        // compute the number of sales without optional product for each package service
        HashMap<String, List<Integer>> salesPkgNoOp = new HashMap<String, List<Integer>>();
        try {
            List<ServicePackage> servicePackages = spService.showPackages();
            servicePackages.forEach(sp -> {
                int noOp = orderService.getNumOfOrderedWithoutOptionalProduct(sp);
                List<Integer> sales = new ArrayList<Integer>();
                sales.add(noOp);
                salesPkgNoOp.put(sp.getPackageName(), sales);
            });
        } catch (CredentialsException e) {
            e.printStackTrace();
        }


        ctx.setVariable("salesPerPackage", salesPerPackage);
        ctx.setVariable("salesPerPackageValidityPeriod", salesPkgValidityPeriod);
        ctx.setVariable("salesPkgNoOp", salesPkgNoOp);


        templateEngine.process("/WEB-INF/EmployeeStatisticsPage.html", ctx, response.getWriter());


    }
}
