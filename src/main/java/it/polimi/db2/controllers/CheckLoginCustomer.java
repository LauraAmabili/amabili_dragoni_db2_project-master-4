package it.polimi.db2.controllers;

import com.sun.xml.bind.v2.TODO;
import it.polimi.db2.entities.UserCustomer;
import it.polimi.db2.entities.UserEmployee;
import it.polimi.db2.exceptions.CredentialsException;
import it.polimi.db2.services.UserCustomerService;
import it.polimi.db2.services.UserEmployeeService;
import org.apache.commons.lang.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ejb.EJB;
import javax.persistence.NonUniqueResultException;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/CheckLoginCustomer")
public class CheckLoginCustomer extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private TemplateEngine templateEngine;

    @EJB(name = "it/polimi/db2/services/UserCustomerService")
    private UserCustomerService usrCustomerService;

    public CheckLoginCustomer(){
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


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());

        // obtain and escape params
        String usrn = null;
        String pwd = null;
        try {
            usrn = StringEscapeUtils.escapeJava(request.getParameter("username"));
            pwd = StringEscapeUtils.escapeJava(request.getParameter("password"));

            if (usrn == null || pwd == null || usrn.isEmpty() || pwd.isEmpty()) {
                throw new Exception("Missing or empty credential value");
            }

        } catch (Exception e) {
            // for debugging only e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing credential value");
            return;
        }
        UserCustomer user;
        try {
            // query db to authenticate for user
            user = usrCustomerService.checkCredentials(usrn, pwd);
        } catch (CredentialsException | NonUniqueResultException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not check credentials");
            return;
        }

        // If the user exists, add info to the session and go to home page, otherwise
        // show login page with error message

        String path;
        if (user == null) {
            ctx.setVariable("errorMsg", "Incorrect username or password");
            path = "/index.html";
            templateEngine.process(path, ctx, response.getWriter());
        } else {

//            QueryService qService = null;
//            try {
//                /*
//                 * We need one distinct EJB for each user. Get the Initial Context for the JNDI
//                 * lookup for a local EJB. Note that the path may be different in different EJB
//                 * environments. In IntelliJ use: ic.lookup(
//                 * "java:/openejb/local/ArtifactFileNameWeb/ArtifactNameWeb/QueryServiceLocalBean"
//                 * );
//                 */
//                InitialContext ic = new InitialContext();
//                // Retrieve the EJB using JNDI lookup
//                qService = (QueryService) ic.lookup("java:/openejb/local/QueryServiceLocalBean");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            request.getSession().setAttribute("user", user);
            // request.getSession().setAttribute("queryService", qService);
            //path = getServletContext().getContextPath() + "/home-page";
            path = getServletContext().getContextPath() + "/home-page";
            response.sendRedirect(path);
        }
    }


}
