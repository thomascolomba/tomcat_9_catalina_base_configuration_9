package com.mycompany.mypackage;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import com.mycompany.packageofmybean.MyBean;

// Extend HttpServlet class
public class ControllerServletMBeanReader extends HttpServlet {
    private String message = "Hello world";
    private String message2;
	private MyBean mBeanCompartidoDesdeGlobal;

    public void init() throws ServletException {
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			mBeanCompartidoDesdeGlobal = (MyBean) envCtx.lookup("bean/MyBeanFactoryDefinedInContextParaReader");
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
		message2 = "foo = " + mBeanCompartidoDesdeGlobal.getFoo() + "foo2 = " + mBeanCompartidoDesdeGlobal.getFoo2() + "foo3 = " + mBeanCompartidoDesdeGlobal.getFoo3() + ", bar = " +mBeanCompartidoDesdeGlobal.getBar();
	}

    public void destroy() {
        // do nothing.
    }
}