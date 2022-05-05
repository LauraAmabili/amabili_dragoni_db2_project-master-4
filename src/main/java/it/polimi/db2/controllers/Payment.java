package it.polimi.db2.controllers;

import it.polimi.db2.entities.*;
import it.polimi.db2.exceptions.CredentialsException;
import it.polimi.db2.external.services.BillingService;
import it.polimi.db2.services.ActivationScheduleService;
import it.polimi.db2.services.OrderService;
import it.polimi.db2.services.PaymentService;
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
import java.util.List;

import static java.lang.Integer.parseInt;
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
        HttpSession session = req.getSession(false);
        Order order = null;
        Date dateStart = null;
        int validityPeriod = 0;
        Date dateEnd = null;
        float totalCost = 0;

        if(session.getAttribute("user") == null) {
            templateEngine.process("/WEB-INF/index.html", ctx, resp.getWriter());
            return;

            // post->  create a new order
        } else if (session.getAttribute("user") != null && req.getSession(false).getAttribute("servicePackageChosen") != null ) {
            ServicePackage servicePackage = (ServicePackage) req.getSession(false).getAttribute("servicePackageChosen");
            validityPeriod = (int) req.getSession(false).getAttribute("chosenValidityPeriod");
            totalCost = (float) req.getSession(false).getAttribute("totalCost");
            dateStart = (Date) req.getSession(false).getAttribute("startDate");
            dateEnd = addMonths(dateStart, validityPeriod);
            try {
                List<OptionalProduct> selectedOptionalProduct = (List<OptionalProduct>) req.getSession(false).getAttribute("selectedOptionalProducts");
                // create new order
                order = orderService.createOrder(validityPeriod, dateStart, totalCost, user, servicePackage, selectedOptionalProduct);
                ctx.setVariable("Order", order);

            } catch (CredentialsException e) {
                e.printStackTrace();
            }
            // order already created
        } else if(session.getAttribute("user") != null && req.getSession(false).getAttribute("orderIdForRejectedPayment")!= null){

            order = (Order) req.getSession(false).getAttribute("order");
            boolean successfulPayment = true;
            orderService.setValid(order,0);
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
            orderService.setValid(order,1);
            asService.addNewActivationRecord(dateStart, dateEnd, order);
            ctx.setVariable("successfulPayment", successfulPayment);
        }
        if (!successfulPayment && order != null) {
            // set valid = 0
            orderService.setValid(order,0);
            user.setSolvent(0);
            Date dateFailed = new Date();
            FailedPayment fp = new FailedPayment();
            fp = paymentService.addFailedPayment(dateFailed, totalCost, user);
            ctx.setVariable("failedPayment", fp);
        }


        ctx.setVariable("successfulPayment", successfulPayment);
        req.getSession(false).setAttribute("order", order);
        req.getSession().setAttribute("user", user);
        templateEngine.process("/WEB-INF/PaymentPage.html", ctx, resp.getWriter());































    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);



    }
}
