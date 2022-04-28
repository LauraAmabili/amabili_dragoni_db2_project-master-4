package it.polimi.db2.controllers;


import it.polimi.db2.entities.Orders;
import it.polimi.db2.entities.ServicePackage;
import it.polimi.db2.entities.UserCustomer;
import it.polimi.db2.exceptions.CredentialsException;
import it.polimi.db2.exceptions.ServicePackageException;
import it.polimi.db2.services.OrderService;
import it.polimi.db2.services.ServicePackageService;
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

//@WebServlet(name = "buyService", value = "/buy-service")
@WebServlet("/package-selected")
public class OnServicePackageSelection extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;
    @EJB(name = "services/ServicePackageService")
    private ServicePackageService spService;

    public OnServicePackageSelection(){
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

//        //get selected service package from the request
//        try {
//            String servicePackageName = (String) req.getParameter("servicePackagename");
//            ctx.setVariable("servicePackageName", servicePackageName);
//
//        } catch (NumberFormatException e) {
//            resp.sendError(500, "Invalid productName");
//        }
//
//
//        if (ctx.getVariable("servicePackageName") == null) {
//
//            String ctxpath = getServletContext().getContextPath();
//            String path = ctxpath + "/BuyServicePage";
//            resp.sendRedirect(path);
//
//        } else {
//            int servicePackageName = (int) ctx.getVariable("servicePackageName");
//
//            //call service to retrieve correct servicePackage
//            ServicePackage sp = spService.findServicePackageById(ser);
//
//            // retrieve all the service packages
//            List<ServicePackage> packageList = null;
//            try {
//                packageList = spService.showPackages();
//
//            } catch (ServicePackageException | CredentialsException e) {
//                response.sendError(500, e.getMessage());
//                return;
//            }
//
//
//            ctx.setVariable("sp",sp);
//            ctx.setVariable("packageList", packageList);
//            templateEngine.process("/WEB-INF/BuyServicePage.html", ctx, response.getWriter());
//
//        }
//    }






        ServicePackage servicePackage = new ServicePackage();
        if(req.getSession(false)!=null  &&  req.getSession(false).getAttribute("user")!=null) {
            //update of object user to make sure is the current one
            try {

                String name = req.getParameter("servicePackageName");
                servicePackage = spService.findServicePackageById(name);
            } catch(CredentialsException e){
                try {
                    throw new ServicePackageException("find Message by Id didn't work");
                } catch (ServicePackageException ex) {
                    ex.printStackTrace();
                }


            }

            req.getSession(false).setAttribute("servicePackageChosen", servicePackage);
            ctx.setVariable("servicePackageChosenCTX", servicePackage);

            templateEngine.process("/WEB-INF/ConfirmationPage.html", ctx, resp.getWriter());
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);



    }
}
