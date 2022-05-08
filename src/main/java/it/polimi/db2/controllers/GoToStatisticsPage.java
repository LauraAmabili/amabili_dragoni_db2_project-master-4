package it.polimi.db2.controllers;

import it.polimi.db2.entities.AuditingTable;
import it.polimi.db2.entities.FailedPayment;
import it.polimi.db2.entities.UserCustomer;
import it.polimi.db2.entities.UserEmployee;
import it.polimi.db2.exceptions.CredentialsException;
import it.polimi.db2.materializedViews.*;
import it.polimi.db2.services.PaymentService;
import it.polimi.db2.services.StatisticsService;
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
import java.util.HashMap;
import java.util.List;

@WebServlet("/go-to-statistics")
public class GoToStatisticsPage extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;

    @EJB(name = "it.polimi.db2.services/StatisticsService")
    private StatisticsService statisticsService;

    @EJB(name = "it.polimi.db2.services/UserCustomerService")
    private UserCustomerService userCustomerService;

    public GoToStatisticsPage() {
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());

        // take the username from the session and set it for the new session
        UserEmployee employee = (UserEmployee) request.getSession(false).getAttribute("employee");
        request.getSession(false).setAttribute("employee", employee);
        ctx.setVariable("loggedEmp", employee);

        // take statistics information from materialized views
        List<TotalPurchasesPerPacket> purchasesPerPackage = statisticsService.getAllTotalTotalPurchasesPacket();
        List<TotalPurchasesPerPacketValidityPeriod> purchasesPerPackageValidityPeriod = statisticsService.getAllTotalPurchasesPacketValidityPeriod();
        List<TotalPackageSales> salesPkgOp = statisticsService.getAllTotalPackageSales();
        List<BestOptional> bestOptionals = statisticsService.getAllBestOptionalProducts();
        List<AveragePackageOptionalProducts>averagePackageOptionalProducts = statisticsService.getAllAveragePackageOptionalProducts();

        // retrieve insolvent users, alerts and suspended orders
        HashMap<UserCustomer, Integer> insolventAlert = new HashMap<>();
        List<UserCustomer> insolvents = statisticsService.getInsolventUsers();
        if(insolvents != null) {
            insolvents.forEach(i -> {
                int hasAlert = statisticsService.userHasAlert(i);
                insolventAlert.put(i, hasAlert);
            });
        }

        HashMap<UserCustomer, List<FailedPayment>> userSuspendedOrders = new HashMap<>();
        try {
            List<UserCustomer> users = userCustomerService.getAllCustomers();
            if(users!= null) {
                users.forEach(u -> {
                    List<FailedPayment> failedPayments = statisticsService.getUsersFailedPayment(u);
                    if(failedPayments.size() != 0) userSuspendedOrders.put(u, failedPayments);
                });
            }

         } catch (CredentialsException e) {
            e.printStackTrace();
        }
        ctx.setVariable("purchasesPerPackage", purchasesPerPackage);
        ctx.setVariable("purchasesPerPackageValidityPeriod", purchasesPerPackageValidityPeriod);
        ctx.setVariable("salesPkgOp", salesPkgOp);
        ctx.setVariable("topOptionalProducts", bestOptionals);
        ctx.setVariable("averagePackageOptionalProducts", averagePackageOptionalProducts);
        ctx.setVariable("insolventAlert", insolventAlert);
        ctx.setVariable("userSuspendedOrders", userSuspendedOrders);


        templateEngine.process("/WEB-INF/EmployeeStatisticsPage.html", ctx, response.getWriter());


    }






    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);

    }
}
