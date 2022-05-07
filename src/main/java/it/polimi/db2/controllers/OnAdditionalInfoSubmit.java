package it.polimi.db2.controllers;


import it.polimi.db2.entities.*;
import it.polimi.db2.exceptions.CredentialsException;
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




        ServicePackage servicePackage = new ServicePackage();
        UserCustomer customer = new UserCustomer();



        int validityPeriod = 0;

        String optionalProductList[] = new String[0];
        List<OptionalProduct> optionalProducts = new ArrayList<>();
        float totalCost = 0;
        Date startDate = new Date();
        if(req.getParameter("orderIdForRejectedPayment") !=null) {

            int orderId = Integer.parseInt(req.getParameter("orderIdForRejectedPayment"));
            req.getSession(false).setAttribute("orderIdForRejectedPayment", orderId);
            Orders order = orderService.getOrder(orderId);
            req.getSession(false).setAttribute("order", order);
            totalCost = order.getTotalCost();
            optionalProducts = order.getOptionalOrdered();
           validityPeriod =order.getValidityPeriodMonth();
           startDate = order.getDateStart();
           servicePackage = order.getOrderedService();



        } else  {
            //update of object user to make sure is the current one

            try {
                servicePackage = spService.findServicePackageById(req.getParameter("servicePackageChosen"));
            } catch (CredentialsException e) {
                e.printStackTrace();
            }
            optionalProductList =((req.getParameterValues("optionalProducts")));
            validityPeriod = parseInt(StringEscapeUtils.escapeJava(req.getParameter("validityPeriod")));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                startDate = (Date) sdf.parse(req.getParameter("date"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (optionalProductList != null) {
                for (String name : optionalProductList) {
                    optionalProducts.add(opService.getOptionalProductById(name));
                }
            }
            float totalOP = (opService.totAmountOptionalProduct(optionalProducts)) * validityPeriod;
            float totalSP = spService.costPerMonth(validityPeriod, servicePackage);
            totalCost = totalOP + totalSP;

            }

        if(req.getSession(false)!=null  &&  req.getSession(false).getAttribute("user")!=null) {
            customer = (UserCustomer) req.getSession(false).getAttribute("user");
            req.getSession(false).setAttribute("loggedCustomer", customer);
            req.getSession(false).setAttribute("totalCost", totalCost);
            req.getSession(false).setAttribute("optionalProducts", optionalProductList);
            req.getSession(false).setAttribute("selectedOptionalProducts", optionalProducts);
            req.getSession(false).setAttribute("chosenValidityPeriod", validityPeriod);
            req.getSession(false).setAttribute("startDate", startDate);
            ctx.setVariable("loggedCustomer", customer);
        }


        ctx.setVariable("chosenValidityPeriod", validityPeriod);
        ctx.setVariable("servicePackageChosenCTX", servicePackage);
        ctx.setVariable("optionalProductsCTX", optionalProducts);
        ctx.setVariable("totalCost", totalCost);
        if(req.getSession(false) == null) {
            req.getSession().setAttribute("chosenValidityPeriod", validityPeriod);
            req.getSession().setAttribute("servicePackageChosenCTX", servicePackage);
            req.getSession().setAttribute("optionalProducts", optionalProducts);
            req.getSession().setAttribute("totalCost", totalCost);
            req.getSession().setAttribute("startDate", startDate);
        }


        templateEngine.process("/WEB-INF/ConfirmationPage.html", ctx, resp.getWriter());


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);



    }

}
