package it.polimi.db2.controllers;

import org.thymeleaf.TemplateEngine;
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


import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

@WebServlet("/create-mobile-phone-service")
public class CreateMobilePhoneService extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB(name = "it.polimi.db2.services/InternetServiceService")
    private ServicesService sService;


    public CreateMobilePhoneService(){
        super();
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = StringEscapeUtils.escapeJava(request.getParameter("name"));

        int minutesNum = parseInt(StringEscapeUtils.escapeJava(request.getParameter("minutesNum")));
        float extraMinFee = parseFloat(StringEscapeUtils.escapeJava(request.getParameter("extraMinFee")));
        int smsNum = parseInt(StringEscapeUtils.escapeJava(request.getParameter("smsNum")));
        float extraSmsFee = parseFloat(StringEscapeUtils.escapeJava(request.getParameter("extraSmsFee")));
        sService.addNewMobilePhoneService(name, minutesNum, smsNum, extraMinFee, extraSmsFee);


        String ctxpath = request.getServletContext().getContextPath();
        String path = ctxpath + "/home-page-employee";
        response.sendRedirect(path);

    }

}


