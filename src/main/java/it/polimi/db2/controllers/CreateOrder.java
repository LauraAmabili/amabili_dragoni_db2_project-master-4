package it.polimi.db2.controllers;

import it.polimi.db2.entities.Order;
import it.polimi.db2.entities.ServicePackage;
import it.polimi.db2.entities.UserCustomer;
import it.polimi.db2.exceptions.CredentialsException;
import it.polimi.db2.exceptions.ServicePackageException;
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

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

@WebServlet("/create-order")
public class CreateOrder extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;
    @EJB(name = "services/ServicePackageService")
    private ServicePackageService spService;

    @EJB(name = "services/OrderService")
    private OrderService orderService;

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


        UserCustomer user = (UserCustomer) req.getSession(false).getAttribute("user");
        String optionalProductList[];
        if(req.getSession(false)!=null  &&  req.getSession(false).getAttribute("user")!=null) {
            //update of object user to make sure is the current one


            Order order = new Order();
            ServicePackage servicePackage = (ServicePackage) req.getSession(false).getAttribute("servicePackageChosen");
            int validityPeriod = (int) req.getSession(false).getAttribute("chosenValidityPeriod");
            optionalProductList =((req.getParameterValues("optionalProducts")));

            try {
                orderService.createOrder(validityPeriod, new Date(), new Date(), 100, user);
            } catch (CredentialsException e) {
                e.printStackTrace();
            }


            ctx.setVariable("monthlyFeeChosen", validityPeriod);
            ctx.setVariable("Order", order);
            templateEngine.process("/WEB-INF/ConfirmationPage.html", ctx, resp.getWriter());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);



    }
}
