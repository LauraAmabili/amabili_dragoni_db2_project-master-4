package it.polimi.db2.controllers;

import it.polimi.db2.entities.InternetService;
import it.polimi.db2.entities.MobilePhoneService;
import it.polimi.db2.entities.OptionalProduct;
import it.polimi.db2.entities.UserEmployee;
import it.polimi.db2.services.OptionalProductService;
import it.polimi.db2.services.ServicesService;
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
import java.util.List;

import static java.lang.Float.parseFloat;

@WebServlet("/create-optional-product-service")
public class CreateOptionalProduct extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;

    @EJB(name = "it.polimi.db2.services/OptionalProductService")
    private OptionalProductService opService;

    @EJB(name = "it.polimi.db2.services/ServicesService")
    private ServicesService sService;

    public CreateOptionalProduct() {
        super();
    }

    public void init() {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        String path;

        // take the username from the session and set it for the new session
        UserEmployee employee = (UserEmployee) request.getSession(false).getAttribute("employee");
        request.getSession(false).setAttribute("employee", employee);
        ctx.setVariable("loggedEmp", employee);

        // take input parameters and check unique name
        String optionalProductName = StringEscapeUtils.escapeJava(request.getParameter("name"));
        if (opService.optionalProductAlreadyExists(optionalProductName)) {
            List<InternetService> fixedInternetServices = sService.getAllFixedInternetServices();
            ctx.setVariable("fixedInternetServices", fixedInternetServices);
            List<InternetService> mobileInternetServices = sService.getAllMobileInternetServices();
            ctx.setVariable("mobileInternetServices", mobileInternetServices);
            List<MobilePhoneService> mobilePhoneServices = sService.getAllMobilePhoneServices();
            ctx.setVariable("mobilePhoneServices", mobilePhoneServices);
            List<OptionalProduct> optionalProducts = opService.getAllOptionalProducts();
            ctx.setVariable("optionalProducts", optionalProducts);
            ctx.setVariable("nameNotUniqueOP", "You have chosen a name that already exists for an Optional Product!");
            path = "/WEB-INF/HomePageEmployee.html";
            templateEngine.process(path, ctx, response.getWriter());
            return;
        }

        String monthlyFeeString = StringEscapeUtils.escapeJava(request.getParameter("monthlyFee"));
        Boolean rightMonthlyFee = monthlyFeeString.matches("[+-]?([0-9]*[.])?[0-9]+");

        if (!rightMonthlyFee) {
            List<InternetService> fixedInternetServices = sService.getAllFixedInternetServices();
            ctx.setVariable("fixedInternetServices", fixedInternetServices);
            List<InternetService> mobileInternetServices = sService.getAllMobileInternetServices();
            ctx.setVariable("mobileInternetServices", mobileInternetServices);
            List<MobilePhoneService> mobilePhoneServices = sService.getAllMobilePhoneServices();
            ctx.setVariable("mobilePhoneServices", mobilePhoneServices);
            List<OptionalProduct> optionalProducts = opService.getAllOptionalProducts();
            ctx.setVariable("optionalProducts", optionalProducts);
            ctx.setVariable("wrongValuesOP", "Please insert correct values!");
            path = "/WEB-INF/HomePageEmployee.html";
            templateEngine.process(path, ctx, response.getWriter());
            return;
        }

        float monthlyFee = parseFloat(monthlyFeeString);
        opService.addNewOptionalProduct(optionalProductName, monthlyFee);
        List<InternetService> fixedInternetServices = sService.getAllFixedInternetServices();
        ctx.setVariable("fixedInternetServices", fixedInternetServices);
        List<InternetService> mobileInternetServices = sService.getAllMobileInternetServices();
        ctx.setVariable("mobileInternetServices", mobileInternetServices);
        List<MobilePhoneService> mobilePhoneServices = sService.getAllMobilePhoneServices();
        ctx.setVariable("mobilePhoneServices", mobilePhoneServices);
        List<OptionalProduct> optionalProducts = opService.getAllOptionalProducts();
        ctx.setVariable("optionalProducts", optionalProducts);
        ctx.setVariable("OKOP", "Optional Product " + optionalProductName + " Correctly inserted!");
        path = "/WEB-INF/HomePageEmployee.html";
        templateEngine.process(path, ctx, response.getWriter());

    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);



    }
}
