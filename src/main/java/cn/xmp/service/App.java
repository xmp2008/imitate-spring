package cn.xmp.service;


import cn.xmp.spring.MyApplicationContext;

public class App {
    public static void main(String[] args) {
        try {
            MyApplicationContext myApplicationContext = new MyApplicationContext(AppConfig.class);
            System.out.println("myApplicationContext.getBean(\"userService\") = " + myApplicationContext.getBean("userService"));
            System.out.println("myApplicationContext.getBean(\"userService\") = " + myApplicationContext.getBean("userService"));
            System.out.println("myApplicationContext.getBean(\"userService\") = " + myApplicationContext.getBean("userService"));
            System.out.println("myApplicationContext.getBean(\"orderService\") = " + myApplicationContext.getBean("orderService"));
            System.out.println("myApplicationContext.getBean(\"orderService\") = " + myApplicationContext.getBean("orderService"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
