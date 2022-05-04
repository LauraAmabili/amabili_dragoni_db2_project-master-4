package it.polimi.db2.controllers;


import it.polimi.db2.entities.*;
import it.polimi.db2.exceptions.CredentialsException;
import it.polimi.db2.services.OptionalOrderedService;
import it.polimi.db2.services.OptionalProductService;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static java.lang.Integer.parseInt;

@WebServlet("/additional-info-selected")
public class OnAdditionalInfoSubmit extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;
    @EJB(name = "services/ServicePackageService")
    private ServicePackageService spService;

    @EJB(name = "services/OptionalProductService")
    private OptionalProductService opService;

    @EJB(name = "services/OrderService")
    private OrderService orderService;

    @EJB(name = "services/OptionalOrderedService")
    private OptionalOrderedService optionalOrderedService;

    public OnAdditionalInfoSubmit(){
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
        HttpSession session = req.getSession(false);

        if(session.getAttribute("user") == null) {
            templateEngine.process("/WEB-INF/index.html", ctx, resp.getWriter());
            return;
        }

        ServicePackage servicePackage;
        servicePackage = (ServicePackage) req.getSession(false).getAttribute("servicePackageChosen");
        UserCustomer customer = (UserCustomer) req.getSession(false).getAttribute("user");

        int validityPeriod = 0; 

        String optionalProductList[];
        List<OptionalProduct> optionalProducts = new ArrayList<>();
        if(req.getSession(false)!=null  &&  req.getSession(false).getAttribute("user")!=null) {
            //update of object user to make sure is the current one

            optionalProductList =((req.getParameterValues("optionalProducts")));
            validityPeriod = parseInt(StringEscapeUtils.escapeJava(req.getParameter("validityPeriod")));
            Date startDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                startDate = (Date) sdf.parse(req.getParameter("date"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (optionalProductList != null) {
                for (String name : optionalProductList) {
                    optionalProducts.add(opService.getOptionalProductById(name));
                    // optionalOrderedService.addOptionalProductToOrder(name,orderId);
                }
            }
            float totalOP = (opService.totAmountOptionalProduct(optionalProducts)) * validityPeriod;
            float totalSP = spService.costPerMonth(validityPeriod, servicePackage);
            float totalCost = totalOP + totalSP;


            req.getSession(false).setAttribute("totalCost", totalCost);
            req.getSession(false).setAttribute("optionalProducts", optionalProductList);
            req.getSession(false).setAttribute("selectedOptionalProducts", optionalProducts);
            req.getSession(false).setAttribute("chosenValidityPeriod", validityPeriod);
            req.getSession(false).setAttribute("startDate", startDate);
            req.getSession(false).setAttribute("loggedCustomer", customer);


                ctx.setVariable("loggedCustomer", customer);
                ctx.setVariable("chosenValidityPeriod", validityPeriod);
                ctx.setVariable("servicePackageChosenCTX", servicePackage);
                ctx.setVariable("optionalProductsCTX", optionalProducts);
                ctx.setVariable("totalCost", totalCost);

                templateEngine.process("/WEB-INF/ConfirmationPage.html", ctx, resp.getWriter());


            }


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);



    }
}
