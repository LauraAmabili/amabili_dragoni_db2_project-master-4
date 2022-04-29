package it.polimi.db2.controllers;

import it.polimi.db2.entities.UserCustomer;
import it.polimi.db2.entities.UserEmployee;
import it.polimi.db2.exceptions.WrongIntException;
import it.polimi.db2.services.ServicesService;
import it.polimi.db2.services.UserEmployeeService;
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


import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

@WebServlet("/create-fixed-internet-service")
public class CreateFixedInternetService extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final int fixed = 1;
    private TemplateEngine templateEngine;

    @EJB(name = "it.polimi.db2.services/InternetServiceService")
    private ServicesService sService;
    private UserEmployeeService userEmployeeService;

    public CreateFixedInternetService(){
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

        // take the username
        UserEmployee employee = (UserEmployee) request.getSession(false).getAttribute("employee");
        request.getSession(false).setAttribute("employee", employee);
        ctx.setVariable("loggedEmp", employee);


        String serviceName = StringEscapeUtils.escapeJava(request.getParameter("name"));
        String gigaNumString = StringEscapeUtils.escapeJava(request.getParameter("gigaNum"));
        String extraGigaFeesString = StringEscapeUtils.escapeJava(request.getParameter("extraFees"));

        String path;
        if (gigaNumString.matches("[0-9]+") && extraGigaFeesString.matches("[+-]?([0-9]*[.])?[0-9]+")) {
            int gigaNum = parseInt(gigaNumString);
            float extraGigaFees = parseFloat(extraGigaFeesString);
            sService.addNewInternetService(serviceName, gigaNum, extraGigaFees, fixed);
            ctx.setVariable("wrongInt", "Service Correctly inserted!");
            path = "/WEB-INF/HomePageEmployee.html";
            templateEngine.process(path, ctx, response.getWriter());
        } else if (!gigaNumString.matches("[0-9]+")){
            ctx.setVariable("wrongInt", "Insert a number for GB!");
            path = "/WEB-INF/HomePageEmployee.html";
            templateEngine.process(path, ctx, response.getWriter());
        } else if(!extraGigaFeesString.matches("[+-]?([0-9]*[.])?[0-9]+")){
            ctx.setVariable("wrongInt", "Insert a number for GB fees!");
            path = "/WEB-INF/HomePageEmployee.html";
            templateEngine.process(path, ctx, response.getWriter());
        } else if (!gigaNumString.matches("[0-9]+") && !extraGigaFeesString.matches("[+-]?([0-9]*[.])?[0-9]+")){
            ctx.setVariable("wrongInt", "Insert a number for GB and GB fees!");
            path = "/WEB-INF/HomePageEmployee.html";
            templateEngine.process(path, ctx, response.getWriter());
        }
    }

    // TODO gestire eccezioni id =
    //problema ora nel nome del looged emp

}


