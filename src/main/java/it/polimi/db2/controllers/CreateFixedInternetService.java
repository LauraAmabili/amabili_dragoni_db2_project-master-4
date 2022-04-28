package it.polimi.db2.controllers;

import it.polimi.db2.services.ServicesService;
import org.apache.commons.lang.StringEscapeUtils;


import javax.ejb.EJB;
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

    @EJB(name = "it.polimi.db2.services/InternetServiceService")
    private ServicesService sService;


    public CreateFixedInternetService(){
        super();
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = StringEscapeUtils.escapeJava(request.getParameter("name"));
        int gigaNum = parseInt(StringEscapeUtils.escapeJava(request.getParameter("gigaNum")));
        float extraGigaFees = parseFloat(StringEscapeUtils.escapeJava(request.getParameter("extraFees")));
        sService.addNewInternetService(name, gigaNum, extraGigaFees, fixed);

        String ctxpath = request.getServletContext().getContextPath();
        String path = ctxpath + "/home-page-employee";
        response.sendRedirect(path);
    }

}


