package it.polimi.db2.controllers;

import it.polimi.db2.entities.InternetService;
import it.polimi.db2.entities.UserEmployee;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.polimi.db2.services.ServicesService;
import org.apache.commons.lang.StringEscapeUtils;


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

@WebServlet("/create-mobile-phone-service")
public class CreateMobilePhoneService extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private TemplateEngine templateEngine;

    @EJB(name = "it.polimi.db2.services/InternetServiceService")
    private ServicesService sService;

    public CreateMobilePhoneService(){
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
        if (sService.mobilePhoneServiceAlreadyExists(serviceName)) {
            List<InternetService> fixedInternetServices = sService.getAllFixedInternetService();
            ctx.setVariable("fixedInternetServices", fixedInternetServices);
            ctx.setVariable("nameNotUnique", "You have chosen a name that already exists for a Mobile Phone Service!");
            path = "/WEB-INF/HomePageEmployee.html";
            templateEngine.process(path, ctx, response.getWriter());
            return;
        }

        String minutesNumString = StringEscapeUtils.escapeJava(request.getParameter("minutesNum"));
        String extraMinutesFeesString = StringEscapeUtils.escapeJava(request.getParameter("extraMinFee"));
        Boolean rightMinutesNum = minutesNumString.matches("[0-9]+");
        Boolean rightMinutesFees = extraMinutesFeesString.matches("[+-]?([0-9]*[.])?[0-9]+");

        String smsNumString = StringEscapeUtils.escapeJava(request.getParameter("smsNum"));
        String extraSmsFeesString = StringEscapeUtils.escapeJava(request.getParameter("extraSmsFee"));
        Boolean rightSmsNum = minutesNumString.matches("[0-9]+");
        Boolean rightSmsFees = extraMinutesFeesString.matches("[+-]?([0-9]*[.])?[0-9]+");

        if (!rightMinutesFees || !rightSmsFees || !rightSmsNum || !rightMinutesNum) {
            List<InternetService> fixedInternetServices = sService.getAllFixedInternetService();
            ctx.setVariable("fixedInternetServices", fixedInternetServices);
            ctx.setVariable("wrongValues", "Please insert correct values!");
            path = "/WEB-INF/HomePageEmployee.html";
            templateEngine.process(path, ctx, response.getWriter());
            return;
        }


        int minutesNum = parseInt(minutesNumString);
        float extraMinFee = parseFloat(extraMinutesFeesString);
        int smsNum = parseInt(smsNumString);
        float extraSmsFee = parseFloat(extraSmsFeesString);

        sService.addNewMobilePhoneService(serviceName, minutesNum, smsNum, extraMinFee, extraSmsFee);

        List<InternetService> fixedInternetServices = sService.getAllFixedInternetService();
        ctx.setVariable("fixedInternetServices", fixedInternetServices);
        ctx.setVariable("OK", "Service " + serviceName + " Correctly inserted!");
        path = "/WEB-INF/HomePageEmployee.html";
        templateEngine.process(path, ctx, response.getWriter());




    }

}


