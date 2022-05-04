package it.polimi.db2.controllers;

import it.polimi.db2.entities.Order;
import it.polimi.db2.entities.ServicePackage;
import it.polimi.db2.entities.UserCustomer;
import it.polimi.db2.exceptions.CredentialsException;
import it.polimi.db2.external.services.BillingService;
import it.polimi.db2.services.OrderService;
import it.polimi.db2.services.ServicePackageService;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

import static java.lang.Integer.parseInt;

@WebServlet("/payment")
public class Payment extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;
    @EJB(name = "services/ServicePackageService")
    private ServicePackageService spService;

    @EJB(name = "services/OrderService")
    private OrderService orderService;

    public Payment(){
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

        UserCustomer user = (UserCustomer) req.getSession(false).getAttribute("user");
        String optionalProductList[];
        HttpSession session = req.getSession(false);


        // in payment ho diverse opzioni->
        // pago l'ordine

        if(session.getAttribute("user") == null) {
            templateEngine.process("/WEB-INF/index.html", ctx, resp.getWriter());
            return;

            // post->  create a new order
        } else if (session.getAttribute("user") != null && req.getSession(false).getAttribute("servicePackageChosen") != null) {
            Order order = new Order();
            ServicePackage servicePackage = (ServicePackage) req.getSession(false).getAttribute("servicePackageChosen");
            int validityPeriod = (int) req.getSession(false).getAttribute("chosenValidityPeriod");
            optionalProductList =((req.getParameterValues("optionalProducts")));
            float totalCost = (int) req.getSession(false).getAttribute("totalCost");

            try {
                orderService.createOrder(validityPeriod, new Date(), new Date(), 100, user, servicePackage);
            } catch (CredentialsException e) {
                e.printStackTrace();
            }

            //payment
            boolean successfulPayment = true;
            if(req.getParameter("makePaymentFail") != null) {
                BillingService ba = new BillingService();
                successfulPayment = ba.attemptPayment(Boolean.parseBoolean(req.getParameter("makePaymentFail")));
                //boolean successfulPayment = ba.attemptPayment(true);
            }

            if(successfulPayment == true){
                order.setValid(1); // TODO CHIAMATA AL DB
                // TODO CREATE SERVICE ACTIVATION SCHEDULE
            }

            ctx.setVariable("successfulPayment", successfulPayment);
            ctx.setVariable("Order", order);
            templateEngine.process("/WEB-INF/PaymentPage.html", ctx, resp.getWriter());

        }





























    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);



    }
}
