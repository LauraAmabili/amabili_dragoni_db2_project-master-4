package it.polimi.db2.controllers;


import it.polimi.db2.entities.Order;
import it.polimi.db2.entities.ServicePackage;
import it.polimi.db2.entities.UserCustomer;
import it.polimi.db2.exceptions.CredentialsException;
import it.polimi.db2.services.OrderService;
import it.polimi.db2.services.ServicePackageService;
import org.apache.commons.lang.StringEscapeUtils;
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
import java.util.Date;
import java.util.List;

import static java.lang.Integer.parseInt;

//@WebServlet(name = "buyService", value = "/buy-service")
@WebServlet("/create-order")
public class OnConfirmClick extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;
    @EJB(name = "services/ServicePackageService")
    private ServicePackageService spService;

    @EJB(name = "services/OrderService")
    private OrderService orderService;


    public OnConfirmClick(){
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
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        final WebContext ctx = new WebContext(req, resp, this.getServletContext(), req.getLocale());

        int validityPeriod = parseInt(StringEscapeUtils.escapeJava((String) req.getSession(false).getAttribute("chosenValidityPeriod")));
        Date date = new Date();
        float totalCost = (float) req.getSession(false).getAttribute("totalCost");
        UserCustomer customer = (UserCustomer) req.getSession(false).getAttribute("user");
        ServicePackage servicePackage = (ServicePackage) req.getSession(false).getAttribute("servicePackageChosen");

        Order order = new Order();
        try {
            order = orderService.createOrder(validityPeriod, date, date, totalCost, customer, servicePackage);
        } catch (CredentialsException e) {
            e.printStackTrace();
        }



        // the user has clicked buy , we create the order and go to payment
        templateEngine.process("/WEB-INF/Payment.html", ctx, resp.getWriter());

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);



    }
}
