/*
package it.polimi.db2.controllers;

import it.polimi.db2.entities.*;
import it.polimi.db2.exceptions.CredentialsException;
import it.polimi.db2.services.OptionalOrderedService;
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
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

@WebServlet("/create-order")
public class CreateOrder extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;
    @EJB(name = "services/ServicePackageService")
    private ServicePackageService spService;
    @EJB(name = "services/OrderService")
    private OrderService oService;
    @EJB(name = "services/OptionalOrderedService")
    private OptionalOrderedService ooService;
    public CreateOrder(){
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


        if(req.getSession(false)!=null  &&  req.getSession(false).getAttribute("user")!=null) {
            int validityPeriod = (int) req.getSession(false).getAttribute("chosenValidityPeriod");
            Date dateStart = (Date) req.getSession(false).getAttribute("startDate");
            float totalCost = (float) req.getSession(false).getAttribute("totalCost");
            UserCustomer user = (UserCustomer) req.getSession(false).getAttribute("loggedCustomer");
            ServicePackage servicePackage = (ServicePackage) req.getSession(false).getAttribute("servicePackageChosen");

            try {
                Order order = oService.createOrder(validityPeriod, dateStart, totalCost, user, servicePackage);
                ctx.setVariable("Order", order);
                List<OptionalProduct> selectedOptionalProduct = (List<OptionalProduct>) req.getSession(false).getAttribute("selectedOptionalProducts");
                selectedOptionalProduct.forEach(sop -> {
                    ooService.addOptionalProductToOrder(sop.getName(), order.getOrderId());
                });
            } catch (CredentialsException e) {
                e.printStackTrace();
            }




            ctx.setVariable("monthlyFeeChosen", validityPeriod);
            templateEngine.process("/WEB-INF/PaymentPage.html", ctx, resp.getWriter());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);



    }
}
*/
