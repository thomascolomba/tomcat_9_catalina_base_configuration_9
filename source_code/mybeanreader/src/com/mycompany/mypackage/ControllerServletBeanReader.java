package com.mycompany.mypackage;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import com.mycompany.packageofmybean.MyBean;

// Extend HttpServlet class
public class ControllerServletBeanReader extends HttpServlet {
    private String message = "Hello world";
    private String message2;
	private MyBean beanCompartidoDesdeGlobal;

    public void init() throws ServletException {
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			beanCompartidoDesdeGlobal = (MyBean) envCtx.lookup("bean/MyBeanFactoryDefinedInContextParaReader");
		} catch(Exception e){
			message2 = e.getMessage();
		}
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		actualizarMessage2();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h1>" + message + "</h1>");
        out.println("<h2>" + message2 + "</h2>");
    }
	
	private void actualizarMessage2(){
		message2 = "foo = " + beanCompartidoDesdeGlobal.getFoo() + "foo2 = " + beanCompartidoDesdeGlobal.getFoo2() + "foo3 = " + beanCompartidoDesdeGlobal.getFoo3() + ", bar = " +beanCompartidoDesdeGlobal.getBar();
	}

    public void destroy() {
        // do nothing.
    }
}