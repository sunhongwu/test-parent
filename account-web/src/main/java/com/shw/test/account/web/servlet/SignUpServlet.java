package com.shw.test.account.web.servlet;

import com.shw.test.account.service.exception.AccountServiceException;
import com.shw.test.account.service.model.SignUpRequest;
import com.shw.test.account.service.service.AccountService;
import com.sun.xml.internal.ws.wsdl.writer.document.StartWithExtensionsType;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class SignUpServlet extends HttpServlet {

    private ApplicationContext context;


    @Override
    public void init() throws ServletException {
        super.init();
        context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        boolean flag = true;

        String id = req.getParameter("id");
        String email = req.getParameter("email");
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String captchaKey = req.getParameter("captchaKey");
        String captchaValue = req.getParameter("captchaValue");

        switch ("id"){
            case "id":
                if (id==null || id.length()==0){
                    flag = false;
                    break;
                }
                if (email==null || email.length()==0){
                    flag = false;
                    break;
                }
                if (name==null || name.length()==0){
                    flag = false;
                    break;
                }
                if (password==null || password.length()==0){
                    flag = false;
                    break;
                }
                if (confirmPassword==null || confirmPassword.length()==0){
                    flag = false;
                    break;
                }
                if (captchaKey==null || captchaKey.length()==0){
                    flag = false;
                    break;
                }
                if (captchaValue==null || captchaValue.length()==0){
                    flag = false;
                    break;
                }
            default:
        }

        if(!flag){
            resp.sendError(400,"Parameter Incomplete.");
            return;
        }

        AccountService service = (AccountService)context.getBean("accountService");

        SignUpRequest request = new SignUpRequest();

        request.setId(id);
        request.setName(name);
        request.setPassword(password);
        request.setConfirmPassword(confirmPassword);
        request.setCaptchaKey(captchaKey);
        request.setCaptchaValue(captchaValue);
        request.setEmail(email);

        request.setActivateServiceUrl(getServletContext().getRealPath("/")+"actavite");

        try {
            service.signUp(request);
            resp.getWriter().print("Account is created,please check your mail box for actiovation link.");
        }catch (AccountServiceException e){
            resp.sendError(400,e.getMessage());
            return;
        }

    }
}
