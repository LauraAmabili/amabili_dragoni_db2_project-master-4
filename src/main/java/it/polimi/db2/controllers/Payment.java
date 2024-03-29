package it.polimi.db2.controllers;

import it.polimi.db2.entities.*;
import it.polimi.db2.exceptions.CredentialsException;
import it.polimi.db2.external.services.BillingService;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import static org.apache.commons.lang.time.DateUtils.addMonths;

@WebServlet("/payment")
public class Payment extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;
    @EJB(name = "services/ServicePackageService")
    private ServicePackageService spService;

    @EJB(name = "services/OrderService")
    private OrderService orderService;

    @EJB(name = "services/ActivationScheduleService")
    private ActivationScheduleService asService;

    @EJB(name = "services/PaymentService")
    private PaymentService paymentService;

    @EJB(name = "services/UserCustomerService")
    private UserCustomerService ucService;


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

        UserCustomer user = (UserCustomer) req.getSession(false).getAttribute("loggedCustomer");
        req.getSession(false).setAttribute("loggedCustomer", user);
        HttpSession session = req.getSession(false);
        Orders order = null;
        Date dateStart = null;
        int validityPeriod = 0;
        Date dateEnd = null;
        float totalCost = 0;
        Date dateFailed = new Date();

        if(req.getSession(false).getAttribute("orderIdForRejectedPayment")!= null){
            int id = (int) req.getSession(false).getAttribute("orderIdForRejectedPayment");
        }


        if(req.getSession(false).getAttribute("loggedCustomer") == null) {
            templateEngine.process("/WEB-INF/index.html", ctx, resp.getWriter());
            return;

            // post->  create a new order
        } else if (req.getSession(false).getAttribute("loggedCustomer")!= null  && req.getSession(false).getAttribute("servicePackageChosen") != null && req.getSession(false).getAttribute("orderIdForRejectedPayment") == null) {
            ServicePackage servicePackage = (ServicePackage) req.getSession(false).getAttribute("servicePackageChosen");
            validityPeriod = (int) req.getSession(false).getAttribute("chosenValidityPeriod");
            totalCost = (float) req.getSession(false).getAttribute("totalCost");
            dateStart = (Date) req.getSession(false).getAttribute("startDate");
            dateEnd = addMonths(dateStart, validityPeriod);
            try {
                List<OptionalProduct> selectedOptionalProduct = (List<OptionalProduct>) req.getSession(false).getAttribute("selectedOptionalProducts");
                // create new order
                order = orderService.createOrder(validityPeriod, dateStart, totalCost, user, servicePackage, selectedOptionalProduct);

            } catch (CredentialsException e) {
                e.printStackTrace();
            }
            // order already created
        } else if(req.getSession(false).getAttribute("loggedCustomer")!= null && req.getSession(false).getAttribute("orderIdForRejectedPayment")!= null){


            order = orderService.getOrder((Integer) req.getSession(false).getAttribute("orderIdForRejectedPayment")) ;
            dateStart = order.getDateStart();
            validityPeriod = order.getValidityPeriodMonth();
            totalCost = order.getTotalCost();
            dateEnd = addMonths(dateStart, validityPeriod);


        }



        boolean successfulPayment = true;
        BillingService ba = new BillingService();
        boolean value = Boolean.parseBoolean((String) req.getParameter("makePaymentFail"));
        successfulPayment = ba.attemptPayment(value);

        if (successfulPayment && order != null) {
            order = orderService.setValid(order, 1);
            asService.addNewActivationRecord(dateStart, dateEnd, order);
            paymentService.updateAuditingTable(user, order.getTotalCost(), dateFailed, successfulPayment);
            paymentService.updateFailedPayments(user, order);
            if( paymentService.getFailedPaymentsOfUser(user) != null && paymentService.getFailedPaymentsOfUser(user).size() < 3 ){
                paymentService.deleteAlert(user);
            }
            if(paymentService.getFailedPaymentsOfUser(user).size() == 0){
                user = ucService.setInsolvent(user, 1);
            }
        }
        if (!successfulPayment && order != null) {
            // set valid = 0
            order = orderService.setValid(order,0);
            user = ucService.setInsolvent(user, 0);
            FailedPayment fp = new FailedPayment();
            fp = paymentService.addFailedPayment(dateFailed, totalCost, user, order);
            if(paymentService.getFailedPaymentsOfUser(user).size()  ==  3 ){
                paymentService.addAuditingTable(user, dateFailed);
            } else if(paymentService.getFailedPaymentsOfUser(user).size() > 3){
                paymentService.updateAuditingTable(user,  order.getTotalCost(), dateFailed, successfulPayment);
            }

        }


        ctx.setVariable("successfulPayment", successfulPayment);
        ctx.setVariable("order", order);
        ctx.setVariable("loggedCustomer", user);
        // req.getSession(false).setAttribute("order", order);
        //req.getSession(false).setAttribute("user", user);
        req.getSession(false).setAttribute("loggedCustomer", user);
        req.getSession(false).removeAttribute("order");
        req.getSession(false).removeAttribute("orderIdForRejectedPayment");
        templateEngine.process("/WEB-INF/PaymentPage.html", ctx, resp.getWriter());































    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);



    }
}
