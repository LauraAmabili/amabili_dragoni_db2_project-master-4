package it.polimi.db2.controllers;

import it.polimi.db2.entities.Order;
import it.polimi.db2.entities.ServicePackage;
import it.polimi.db2.entities.UserEmployee;
import it.polimi.db2.exceptions.CredentialsException;
import it.polimi.db2.services.*;
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

    @EJB(name = "it/polimi/db2/services/OptionalProductService")
    private OptionalProductService opService;

    @EJB(name = "it/polimi/db2/services/OptionalOrderedService")
    private OptionalOrderedService ooService;


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


        HashMap<String, Integer> salesPerPackage = new HashMap<String, Integer>();
        HashMap<String, List<Integer>> salesPkgValidityPeriod = new HashMap<String, List<Integer>>();
        HashMap<String, HashMap<String, Integer>> salesOp = new HashMap<String, HashMap<String, Integer>>();
        HashMap<String, Float> avgOptionalSalesPerPackage = new HashMap<String, Float>();

        try {
            List<ServicePackage> servicePackages = spService.showPackages();
            // for each service package
            servicePackages.forEach(sp -> {
                // compute the number of sales for each service package
                salesPerPackage.put(sp.getPackageName(), orderService.getNumberOfSalesByServicePkg(sp));

                // compute the number of sales for each service package for each monthly fee
                int sales12 = orderService.getNumberOfSalesByServicePkgValidityPeriod(sp, 12);
                int sales24 = orderService.getNumberOfSalesByServicePkgValidityPeriod(sp, 24);
                int sales36 = orderService.getNumberOfSalesByServicePkgValidityPeriod(sp, 36);
                List<Integer> sales = new ArrayList<Integer>();
                sales.add(sales12);
                sales.add(sales24);
                sales.add(sales36);
                salesPkgValidityPeriod.put(sp.getPackageName(), sales);

                // compute the number of sales without optional product for each package service
                HashMap<String, Integer> spOp = new HashMap<String, Integer>();
                // number of sales without optional products
                int noOp = orderService.getNumOfOrderedWithoutOptionalProduct(sp);
                spOp.put("without optional products", noOp);
                try {
                    List<String> servicePkgOptionalProducts = opService.showServicePackageOptionalProducts(sp);
                    // for each optional product of the service package
                    if (servicePkgOptionalProducts != null) {
                        // number of sales of service package with that optional product
                        servicePkgOptionalProducts.forEach(servicePkgOptionalProduct -> {
                            int opNum = orderService.getNumOfOrderedWithOptionalProduct(sp, servicePkgOptionalProduct);
                            // number of sales for the package service for each optional product
                            spOp.put("with " + servicePkgOptionalProduct, opNum);
                            // average number of optional products sold together with each service package
                        });
                    }
                    // insert founded values in the hash map
                    salesOp.put(sp.getPackageName(), spOp);

                } catch (CredentialsException e) {
                    e.printStackTrace();
                }

                // avg number of optional products selected with each service package by customers

                // total order for the service package
                float totSales = orderService.getNumberOfSalesByServicePkg(sp);

                List<Order> spOrders = orderService.getServicePackageOrders(sp);
                // for each order, number of optional products selected
                List<Integer> opNum = new ArrayList<>();
                spOrders.forEach(o -> {
                    opNum.add(ooService.numberOfOrderOptionalProduct(o.getOrderId()));
                });

                float totOpNum = opNum.stream().mapToInt(i -> i).sum();


                if (totSales != 0) { avgOptionalSalesPerPackage.put(sp.getPackageName(), totOpNum/totSales); }
                else {avgOptionalSalesPerPackage.put(sp.getPackageName(), (float)0); }

            });
        } catch (CredentialsException e) {
            e.printStackTrace();
        }


        ctx.setVariable("salesPerPackage", salesPerPackage);
        ctx.setVariable("salesPerPackageValidityPeriod", salesPkgValidityPeriod);
        ctx.setVariable("salesPkgOp", salesOp);
        ctx.setVariable("avgOptionalSalesPerPackage", avgOptionalSalesPerPackage);

        templateEngine.process("/WEB-INF/EmployeeStatisticsPage.html", ctx, response.getWriter());


    }
}
