package it.polimi.db2.controllers;

import it.polimi.db2.entities.OptionalProduct;
import it.polimi.db2.entities.Order;
import it.polimi.db2.entities.ServicePackage;
import it.polimi.db2.entities.UserCustomer;
import it.polimi.db2.exceptions.CredentialsException;
import it.polimi.db2.external.services.BillingService;
import it.polimi.db2.services.ActivationScheduleService;
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

        if(session.getAttribute("user") == null) {
            templateEngine.process("/WEB-INF/index.html", ctx, resp.getWriter());
            return;

            // post->  create a new order
        } else if (session.getAttribute("user") != null && req.getSession(false).getAttribute("servicePackageChosen") != null ) {

            ServicePackage servicePackage = (ServicePackage) req.getSession(false).getAttribute("servicePackageChosen");
            int validityPeriod = (int) req.getSession(false).getAttribute("chosenValidityPeriod");
            float totalCost = (float) req.getSession(false).getAttribute("totalCost");
            Date dateStart = (Date) req.getSession(false).getAttribute("startDate");
            Date dateEnd = addMonths(dateStart, validityPeriod);

            try {
                List<OptionalProduct> selectedOptionalProduct = (List<OptionalProduct>) req.getSession(false).getAttribute("selectedOptionalProducts");
                Order order = orderService.createOrder(validityPeriod, dateStart, totalCost, user, servicePackage, selectedOptionalProduct);
                ctx.setVariable("Order", order);


                //payment
                boolean successfulPayment = true;
                BillingService ba = new BillingService();
                boolean value = Boolean.parseBoolean((String) req.getSession(false).getAttribute("makePaymentFail"));
                successfulPayment = ba.attemptPayment(value);

                if (successfulPayment && order != null) {
                    order.setValid(1);
                    Order o = order;
                    asService.addNewActivationRecord(dateStart, dateEnd, order);
                    ctx.setVariable("successfulPayment", successfulPayment);
                }
                if (!successfulPayment) {
                    order.setValid(0);
                    user.setSolvent(0);

                    // TODO add to failed payment
                }

                req.getSession(false).setAttribute("order", order);
                templateEngine.process("/WEB-INF/PaymentPage.html", ctx, resp.getWriter());
            } catch (CredentialsException e) {
                e.printStackTrace();
            }

        }































    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);



    }
}
