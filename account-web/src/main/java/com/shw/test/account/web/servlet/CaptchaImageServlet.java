package com.shw.test.account.web.servlet;

import com.shw.test.account.service.exception.AccountServiceException;
import com.shw.test.account.service.service.AccountService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class CaptchaImageServlet extends HttpServlet {

    private ApplicationContext context;


    @Override
    public void init() throws ServletException {
        super.init();
        context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String key = req.getParameter("key");
        if(key == null || key.length() == 0){
            resp.sendError(400,"No Captcha Key Found.");
        }else{
            AccountService service = (AccountService)context.getBean("accountService");
            try {
                resp.setContentType("image/jpeg");
                OutputStream outputStream = resp.getOutputStream();
                outputStream.write(service.generateCaptchaImage(key));
                outputStream.close();
            }catch (AccountServiceException e){
                resp.sendError(404,e.getMessage());
            }
        }
    }
}
