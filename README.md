# tomcat_9_catalina_base_configuration_9

Se expone un java MBean a una webapp.

proyecto de MBean con su código fuente y build de Ant : ./source_code/mymbean/

proyecto de la webapp que enseña el MBean con su código fuente y build de Ant : ./source_code/mymbeanreader/

Compilar y desplegar el jar del MBean se hace con : ./compileanddeploy.bat
(para el MBean :  ant clean compile jar + copiar el jar en la carpeta lib/ de CATALINA_BASE
para la webapp : ant clean compile dist  + copiar el war en la carpeta webapps/ de CATALINA_BASE)

1)Al acceder a la webapp reader : http://localhost:8080/mymbeanreader/ControllerServletMBeanReader
Se ve que el atributo foo tiene el valor de configuración del CATALINA_BASE/conf/server.xml : foo = value of foo set from server.xml in CATALINA_BASEfoo2 = Default Foo2 from MyBean.javafoo3 = Default Foo3 from MyBean.java, bar = 24

2)Al acceder a la webapp writer : http://localhost:8080/mymbeanreader/ControllerServletMBeanWriter
Se ve el mensaje : El valor de foo del MBean ha sido cambiado.

3)Al volver a actualizar la webapp reader : http://localhost:8080/mymbeanreader/ControllerServletMBeanReader
Se ve que el atributo foo tiene el valor de modificado por la webapp writer : foo = Valor de foo cambiada desde ControllerServletMBeanWriterfoo2 = Default Foo2 from MyBean.javafoo3 = Default Foo3 from MyBean.java, bar = 24


------------ CATALINA_BASE/conf/server.xml
en la etiqueta <GlobalNamingResources> :
	<Resource name="bean/MyBeanFactory" auth="Container"
		type="com.mycompany.packageofmybean.MyBean"
		factory="org.apache.naming.factory.BeanFactory"
		bar="24" foo="value of foo set from server.xml in CATALINA_BASE"/>
Luego :
	<Context docBase="mymbeanreader.war">
		<ResourceLink name="bean/MyBeanFactoryDefinedInContextParaReader"
		global="bean/MyBeanFactory"
		type="com.mycompany.packageofmybean.MyBean"/>
	</Context>

	<Context docBase="mymbeanwriter.war">
		<ResourceLink name="bean/MyBeanFactoryDefinedInContextParaWriter"
		global="bean/MyBeanFactory"
		type="com.mycompany.packageofmybean.MyBean"/>
	</Context>

------------ MBean
public class MyBean {

  private String foo = "Default Foo from MyBean.java";
  private String foo2 = "Default Foo2 from MyBean.java";
  private String foo3 = "Default Foo3 from MyBean.java";

------------ webapp reader, ControllerServletMBeanReader.java
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
	// Set response content type
	response.setContentType("text/html");
	// Actual logic goes here.
	PrintWriter out = response.getWriter();
	out.println("<h1>" + message + "</h1>");
	out.println("<h2>" + message2 + "</h2>");
}
private void actualizarMessage2(){
	message2 = "foo = " + mBeanCompartidoDesdeGlobal.getFoo() + "foo2 = " + mBeanCompartidoDesdeGlobal.getFoo2() + "foo3 = " + mBeanCompartidoDesdeGlobal.getFoo3() + ", bar = " +mBeanCompartidoDesdeGlobal.getBar();
}
------------ webapp reader, web.xml
<resource-ref>
  <description>Object factory for MyBean instances.</description>
  <res-ref-name>bean/MyBeanFactoryDefinedInContextParaReader</res-ref-name>
  <res-ref-type>com.mycompany.packageofmybean.MyBean</res-ref-type>
  <res-auth>Container</res-auth>
</resource-ref>


------------ webapp writer, ControllerServletMBeanWriter.java
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
------------ webapp writer, web.xml
<resource-ref>
  <description>Object factory for MyBean instances.</description>
  <res-ref-name>bean/MyBeanFactoryDefinedInContextParaWriter</res-ref-name>
  <res-ref-type>com.mycompany.packageofmybean.MyBean</res-ref-type>
  <res-auth>Container</res-auth>
</resource-ref>
