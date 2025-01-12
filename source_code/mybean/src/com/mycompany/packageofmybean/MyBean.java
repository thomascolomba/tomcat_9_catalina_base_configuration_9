package com.mycompany.packageofmybean;

public class MyBean {

  private String foo = "Default Foo from MyBean.java";
  private String foo2 = "Default Foo2 from MyBean.java";
  private String foo3 = "Default Foo3 from MyBean.java";

  public String getFoo() {
    return (this.foo);
  }

  public void setFoo(String foo) {
    this.foo = foo;
  }
  
  public String getFoo3() {
    return (this.foo3);
  }

  public void setFoo3(String foo3) {
    this.foo3 = foo3;
  }
  
  public String getFoo2() {
    return (this.foo2);
  }

  public void setFoo2(String foo2) {
    this.foo2 = foo2;
  }

  private int bar = 0;

  public int getBar() {
    return (this.bar);
  }

  public void setBar(int bar) {
    this.bar = bar;
  }


}