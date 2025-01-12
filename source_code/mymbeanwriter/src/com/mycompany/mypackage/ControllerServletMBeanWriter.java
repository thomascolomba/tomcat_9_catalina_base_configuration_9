package com.mycompany.mypackage;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import com.mycompany.packageofmybean.MyBean;

// Extend HttpServlet class
public class ControllerServletMBeanWriter extends HttpServlet {
    private String message = "El valor de foo del MBean ha sido cambiado.";
	private MyBean mBeanGlobalDeTomcat;

    public void init() throws ServletException {
        // Do required initialization
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			mBeanGlobalDeTomcat = (MyBean) envCtx.lookup("bean/MyBeanFactoryDefinedInContextParaWriter");
		} catch(Exception e){
			message = e.getMessage();
		}
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		mBeanGlobalDeTomcat.setFoo("Valor de foo cambiada desde ControllerServletMBeanWriter");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h1>" + message + "</h1>");
    }

    public void destroy() {
        // do nothing.
    }
}