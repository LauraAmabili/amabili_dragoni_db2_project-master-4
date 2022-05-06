package it.polimi.db2.controllers;


import it.polimi.db2.entities.*;
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
import java.util.List;



// this page should show to the client all the packets with monthly fee annd optional products
// after you choose your package + optional product + validity period you go to create order where you have the overview

@WebServlet(name = "homePage", value = "/home-page-customer")
public class HomePageCustomer extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;
    @EJB(name = "services/ServicePackageService")
    private ServicePackageService sps;
    @EJB(name = "services/UserCustomerService")
    private UserCustomerService userCustomerService;
    @EJB(name = "services/OrderService")
    private OrderService orderService;

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

        List<ServicePackage> sp = null;
        try {
            sp = sps.showPackages();
        } catch (CredentialsException e) {
            e.printStackTrace();
        }

        final WebContext ctx = new WebContext(req, resp, this.getServletContext(), req.getLocale());
        if(req.getSession(false)!=null  &&  req.getSession(false).getAttribute("user")!=null) {
            //update of object user to make sure is the current one
            UserCustomer customer = userCustomerService.findCustomerById((UserCustomer) req.getSession().getAttribute("user"));
            if(customer.getSolvent() == 0 ){
                List<Orders> rejectedOrders = orderService.getNotValidOrdersOfUser(customer);
                ctx.setVariable("rejectedOrders", rejectedOrders);
            }

            req.getSession(false).setAttribute("customer", customer);
            ctx.setVariable("loggedCustomer", customer);


        }

        ctx.setVariable("packageList", sp);
        templateEngine.process("/WEB-INF/HomePageForCustomer.html", ctx, resp.getWriter());


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
        
  

    }
}
