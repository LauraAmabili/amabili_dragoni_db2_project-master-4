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

@WebServlet("/create-service")
public class CreateService extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB(name = "it.polimi.db2.services/InternetServiceService")
    private ServicesService sService;


    public CreateService(){
        super();
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String serviceType, name;
        serviceType = StringEscapeUtils.escapeJava(request.getParameter("serviceType"));
        name = StringEscapeUtils.escapeJava(request.getParameter("name"));

        //switch (serviceType){
          //  case "mobilePhone":
            /*    int minutesNum, smsNum;
                float extraSmsFee, extraMinFee;
                minutesNum = parseInt(StringEscapeUtils.escapeJava(request.getParameter("minutesNum")));
                extraMinFee = parseFloat(StringEscapeUtils.escapeJava(request.getParameter("extraMinFees")));
                smsNum = parseInt(StringEscapeUtils.escapeJava(request.getParameter("SMSnum")));
                extraSmsFee = parseFloat(StringEscapeUtils.escapeJava(request.getParameter("extraSMSfees")));
                sService.addNewMobilePhoneService(name, minutesNum, smsNum, extraMinFee, extraSmsFee);*/

           // case "fixedInternet":
                int gigaNum;
                float extraGigaFees;
                String usrn;
                usrn = StringEscapeUtils.escapeJava(request.getParameter("gigaNum"));
                gigaNum = parseInt(StringEscapeUtils.escapeJava(request.getParameter("gigaNum")));
                extraGigaFees = parseFloat(StringEscapeUtils.escapeJava(request.getParameter("extraFees")));
                sService.addNewInternetService(name, gigaNum, extraGigaFees, 0);

         /*   case "mobileInternet":
                gigaNum = parseInt(StringEscapeUtils.escapeJava(request.getParameter("gigaNum")));
                extraGigaFees = parseFloat(StringEscapeUtils.escapeJava(request.getParameter("extraGigaFees")));
                sService.addNewInternetService(name, gigaNum, extraGigaFees, 1);*/
        //}
        String ctxpath = request.getServletContext().getContextPath();
        String path = ctxpath + "/home-page-employee";
        response.sendRedirect(path);

    }

}


