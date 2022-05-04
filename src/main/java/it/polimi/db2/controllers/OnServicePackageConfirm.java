package it.polimi.db2.controllers;


import it.polimi.db2.entities.*;
import it.polimi.db2.exceptions.CredentialsException;
import it.polimi.db2.exceptions.ServicePackageException;
import it.polimi.db2.services.OptionalProductService;
import it.polimi.db2.services.ServicePackageService;
import it.polimi.db2.services.ServicesService;
import it.polimi.db2.services.UserCustomerService;
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
import java.util.List;


@WebServlet("/package-confirmed")
public class OnServicePackageConfirm extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;
    @EJB(name = "services/ServicePackageService")
    private ServicePackageService spService;

    @EJB(name = "services/OptionalProductService")
    private OptionalProductService opService;

    @EJB(name = "services/ServicesService")
    private ServicesService sService;

    @EJB(name = "services/UserCustomerService")
    private UserCustomerService userCustomerService;

    public OnServicePackageConfirm(){
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
        ServicePackage servicePackage = new ServicePackage();
        ServicePackage service = (ServicePackage) req.getSession(false).getAttribute("servicePackageChosen");
        try {
            servicePackage = spService.findServicePackageById(service.getPackageName());
        } catch (CredentialsException e) {
            e.printStackTrace();
        }

        List<String> optionalProductsIds = null;
        List<OptionalProduct> optionalProducts = new ArrayList<>();
        try {
            optionalProductsIds = opService.showServicePackageOptionalProducts(servicePackage);
            if(optionalProductsIds!=null) {
                for (String optionalProductsId : optionalProductsIds) {
                    optionalProducts.add(opService.getOptionalProductById(optionalProductsId));
                }
            }
        } catch (CredentialsException e) {
            e.printStackTrace();
        }

        UserCustomer customer = userCustomerService.findCustomerById((UserCustomer) req.getSession().getAttribute("user"));
        ctx.setVariable("loggedCustomer", customer);
        req.getSession(false).setAttribute("user", customer);
        req.getSession(false).setAttribute("servicePackageChosen", servicePackage);
        ctx.setVariable("servicePackageChosenCTX", servicePackage);
        ctx.setVariable("optionalProductsObjects", optionalProducts);
        templateEngine.process("/WEB-INF/AdditionalInformation.html", ctx, resp.getWriter());



    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);



    }
}
