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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static java.lang.Integer.parseInt;

@WebServlet("/period-selected")
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


        ServicePackage servicePackage;
        UserCustomer user = (UserCustomer) req.getSession(false).getAttribute("user");


        servicePackage = (ServicePackage) req.getSession(false).getAttribute("servicePackageChosen");
        int validityPeriod = 0; 

        String optionalProductList[];
        List<OptionalProduct> optionalProducts = new ArrayList<>();
        if(req.getSession(false)!=null  &&  req.getSession(false).getAttribute("user")!=null) {
            //update of object user to make sure is the current one



            optionalProductList =((req.getParameterValues("optionalProducts")));


            optionalProductList =((req.getParameterValues("optionalProducts")));
            validityPeriod = parseInt(StringEscapeUtils.escapeJava(req.getParameter("validityPeriod")));

            if (optionalProductList != null) {
                for (String name : optionalProductList) {
                    optionalProducts.add(opService.getOptionalProductById(name));
                }

            if(optionalProductList!= null) {
                for (String name : optionalProductList) {
                    optionalProducts.add(opService.getOptionalProductById(name));
                }
            }


            Order order = new Order();
            try {
                order = orderService.createOrder(validityPeriod, new Date(), new Date(), 100, user, servicePackage);
            } catch (CredentialsException e) {
                e.printStackTrace();
            }


                req.getSession(false).setAttribute("optionalProducts", optionalProductList);
                req.getSession(false).setAttribute("chosenValidityPeriod", validityPeriod);
                ctx.setVariable("chosenValidityPeriod", validityPeriod);
                ctx.setVariable("servicePackageChosenCTX", servicePackage);
                ctx.setVariable("optionalProductsCTX", optionalProducts);

                templateEngine.process("/WEB-INF/ConfirmationPage.html", ctx, resp.getWriter());


            }

        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);



    }
}
