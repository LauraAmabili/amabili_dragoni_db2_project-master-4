package it.polimi.db2.controllers;

import it.polimi.db2.entities.*;
import it.polimi.db2.services.MonthlyFeesService;
import it.polimi.db2.services.OptionalProductService;
import it.polimi.db2.services.ServicePackageService;
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

@WebServlet("/create-service-package")
public class CreateNewServicePackage extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;

    @EJB(name = "it.polimi.db2.services/ServicePackageService")
    private ServicePackageService sPkgService;

    @EJB(name = "it.polimi.db2.services/MonthlyFeesService")
    private MonthlyFeesService mfService;

    @EJB(name = "it.polimi.db2.services/ServicesService")
    private ServicesService sService;

    @EJB(name = "it.polimi.db2.services/OptionalProductService")
    private OptionalProductService opService;

    public CreateNewServicePackage() {
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


        // take the name of the package and check it is unique
        String packageName = StringEscapeUtils.escapeJava(request.getParameter("name"));
        if(sPkgService.servicePackageAlreadyExists(packageName)) {
            List<InternetService> fixedInternetServices = sService.getAllFixedInternetServices();
            ctx.setVariable("fixedInternetServices", fixedInternetServices);
            List<InternetService> mobileInternetServices = sService.getAllMobileInternetServices();
            ctx.setVariable("mobileInternetServices", mobileInternetServices);
            List<MobilePhoneService> mobilePhoneServices = sService.getAllMobilePhoneServices();
            ctx.setVariable("mobilePhoneServices", mobilePhoneServices);
            List<OptionalProduct> optionalProducts = opService.getAllOptionalProducts();
            ctx.setVariable("optionalProducts", optionalProducts);
            ctx.setVariable("namePkgNotUnique", "You have chosen a name that already exists for your Package!");
            path = "/WEB-INF/HomePageEmployee.html";
            templateEngine.process(path, ctx, response.getWriter());
            return;
        }

        // take the prices of the package, check they are numbers.
        String packagePrice12String = StringEscapeUtils.escapeJava(request.getParameter("pkgPrice12"));
        Boolean rightPkgPrice12 = packagePrice12String.matches("[+-]?([0-9]*[.])?[0-9]+");
        String packagePrice24String = StringEscapeUtils.escapeJava(request.getParameter("pkgPrice24"));
        Boolean rightPkgPrice24 = packagePrice24String.matches("[+-]?([0-9]*[.])?[0-9]+");
        String packagePrice36String = StringEscapeUtils.escapeJava(request.getParameter("pkgPrice36"));
        Boolean rightPkgPrice36 = packagePrice36String.matches("[+-]?([0-9]*[.])?[0-9]+");

        if(!rightPkgPrice12 || !rightPkgPrice24 || !rightPkgPrice36) {
            List<InternetService> fixedInternetServices = sService.getAllFixedInternetServices();
            ctx.setVariable("fixedInternetServices", fixedInternetServices);
            List<InternetService> mobileInternetServices = sService.getAllMobileInternetServices();
            ctx.setVariable("mobileInternetServices", mobileInternetServices);
            List<MobilePhoneService> mobilePhoneServices = sService.getAllMobilePhoneServices();
            ctx.setVariable("mobilePhoneServices", mobilePhoneServices);
            List<OptionalProduct> optionalProducts = opService.getAllOptionalProducts();
            ctx.setVariable("optionalProducts", optionalProducts);
            ctx.setVariable("wrongValuesPkg", "Please insert correct values for fees!");
            path = "/WEB-INF/HomePageEmployee.html";
            templateEngine.process(path, ctx, response.getWriter());
            return;
        }

        float price12 = parseFloat(packagePrice12String);
        float price24 = parseFloat(packagePrice24String);
        float price36 = parseFloat(packagePrice36String);

        MonthlyFee mf = mfService.addGetNewMonthlyFeeService(price12, price24, price36);

        // take the fixed phone parameter
        String[] fixedPhoneString = request.getParameterValues("fixedPhone");
        int fixedPhone = 1;
        if (fixedPhoneString == null) { fixedPhone = 0; }

        // create the new service package
        sPkgService.addNewServicePackage(packageName, fixedPhone, mf);

        // take selected mobile/fixed internet services and add them in the PkgServiceInternet
        String[] myMobileInternetServices = request.getParameterValues("mobileInternetService");
        if (myMobileInternetServices!= null) {
            for (String mobileInternetService : myMobileInternetServices) {
                sService.addNewPkgInternetService(packageName, mobileInternetService);
            }
        }
        String[] myFixedInternetServices = request.getParameterValues("fixedInternetService");
        if(myFixedInternetServices != null) {
            for (String fixedInternetService : myFixedInternetServices) {
                sService.addNewPkgInternetService(packageName, fixedInternetService);
            }
        }

        // take selected mobile phone services and add them in the PkgServicePhone
        String[] myMobilePhoneServices = request.getParameterValues("mobilePhoneService");
        if (myMobilePhoneServices != null) {
            for (String mobilePhoneService : myMobilePhoneServices) {
                String mb = mobilePhoneService;
                sService.addNewPkgPhoneService(packageName, mobilePhoneService);
            }
        }

        // take selected optional products and add them in the PkgServiceOptional
        String[] optionalProductServices = request.getParameterValues("optionalProductService");
        if(optionalProductServices != null) {
            for (String optionalProductService : optionalProductServices) {
                opService.addNewPkgOptionalProduct(packageName, optionalProductService);
            }
        }


        List<InternetService> fixedInternetServices = sService.getAllFixedInternetServices();
        ctx.setVariable("fixedInternetServices", fixedInternetServices);
        List<InternetService> mobileInternetServices = sService.getAllMobileInternetServices();
        ctx.setVariable("mobileInternetServices", mobileInternetServices);
        List<MobilePhoneService> mobilePhoneServices = sService.getAllMobilePhoneServices();
        ctx.setVariable("mobilePhoneServices", mobilePhoneServices);
        List<OptionalProduct> optionalProducts = opService.getAllOptionalProducts();
        ctx.setVariable("optionalProducts", optionalProducts);
        ctx.setVariable("OKPKG", "Service Pakage " + packageName + " Correctly inserted!");
        path = "/WEB-INF/HomePageEmployee.html";
        templateEngine.process(path, ctx, response.getWriter());


    }



}