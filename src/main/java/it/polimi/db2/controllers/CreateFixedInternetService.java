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
import static java.lang.Integer.parseInt;

@WebServlet("/create-fixed-internet-service")
public class CreateFixedInternetService extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final int fixed = 1;
    private TemplateEngine templateEngine;

    @EJB(name = "it.polimi.db2.services/ServicesService")
    private ServicesService sService;

    @EJB(name = "it.polimi.db2.services/OptionalProductService")
    private OptionalProductService opService;

    public CreateFixedInternetService() {
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
        String serviceName = StringEscapeUtils.escapeJava(request.getParameter("name"));
        if (sService.internetServiceAlreadyExists(serviceName)) {
            List<InternetService> fixedInternetServices = sService.getAllFixedInternetServices();
            ctx.setVariable("fixedInternetServices", fixedInternetServices);
            List<InternetService> mobileInternetServices = sService.getAllMobileInternetServices();
            ctx.setVariable("mobileInternetServices", mobileInternetServices);
            List<MobilePhoneService> mobilePhoneServices = sService.getAllMobilePhoneServices();
            ctx.setVariable("mobilePhoneServices", mobilePhoneServices);
            List<OptionalProduct> optionalProducts = opService.getAllOptionalProducts();
            ctx.setVariable("optionalProducts", optionalProducts);
            ctx.setVariable("nameNotUnique", "You have chosen a name that already exists for an Internet Service!");
            path = "/WEB-INF/HomePageEmployee.html";
            templateEngine.process(path, ctx, response.getWriter());
            return;
        }

        String gigaNumString = StringEscapeUtils.escapeJava(request.getParameter("gigaNum"));
        String extraGigaFeesString = StringEscapeUtils.escapeJava(request.getParameter("extraFees"));

        Boolean rightGigaNum = gigaNumString.matches("[0-9]+");
        Boolean rightGigaFees = extraGigaFeesString.matches("[+-]?([0-9]*[.])?[0-9]+");


        if (!rightGigaFees || !rightGigaNum) {
            List<InternetService> fixedInternetServices = sService.getAllFixedInternetServices();
            ctx.setVariable("fixedInternetServices", fixedInternetServices);
            List<InternetService> mobileInternetServices = sService.getAllMobileInternetServices();
            ctx.setVariable("mobileInternetServices", mobileInternetServices);
            List<MobilePhoneService> mobilePhoneServices = sService.getAllMobilePhoneServices();
            ctx.setVariable("mobilePhoneServices", mobilePhoneServices);
            List<OptionalProduct> optionalProducts = opService.getAllOptionalProducts();
            ctx.setVariable("optionalProducts", optionalProducts);
            ctx.setVariable("wrongValues", "Please insert correct values!");
            path = "/WEB-INF/HomePageEmployee.html";
            templateEngine.process(path, ctx, response.getWriter());
            return;
        }

        int gigaNum = parseInt(gigaNumString);
        float extraGigaFees = parseFloat(extraGigaFeesString);

        sService.addNewInternetService(serviceName, gigaNum, extraGigaFees, fixed);

        List<InternetService> fixedInternetServices = sService.getAllFixedInternetServices();
        ctx.setVariable("fixedInternetServices", fixedInternetServices);
        List<InternetService> mobileInternetServices = sService.getAllMobileInternetServices();
        ctx.setVariable("mobileInternetServices", mobileInternetServices);
        List<MobilePhoneService> mobilePhoneServices = sService.getAllMobilePhoneServices();
        ctx.setVariable("mobilePhoneServices", mobilePhoneServices);
        List<OptionalProduct> optionalProducts = opService.getAllOptionalProducts();
        ctx.setVariable("optionalProducts", optionalProducts);

        ctx.setVariable("OK", "Service " + serviceName + " Correctly inserted!");
        path = "/WEB-INF/HomePageEmployee.html";
        templateEngine.process(path, ctx, response.getWriter());

    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

}


