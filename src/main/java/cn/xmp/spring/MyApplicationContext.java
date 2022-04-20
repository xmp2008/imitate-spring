package cn.xmp.spring;

import java.beans.Introspector;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Title:
 * @BelongProjecet imitate-spring
 * @BelongPackage cn.xmp.spring
 * @Description: 容器主类
 * @Author: xmp
 * @Date: 2022/4/19 16:04
 */
public class MyApplicationContext {

    private Class aClass;

    private ConcurrentHashMap<String, BeanDefinition> beanDefinitionConcurrentHashMap = new ConcurrentHashMap<>();

    private ConcurrentHashMap<String, Object> singletonConcurrentHashMap = new ConcurrentHashMap<>();

    public MyApplicationContext(Class aClass) throws ClassNotFoundException {
        this.aClass = aClass;
        //扫描
        if (aClass.isAnnotationPresent(ComponentScan.class)) {
            ComponentScan componentScanAnnotation = (ComponentScan) aClass.getAnnotation(ComponentScan.class);
//            System.out.println("componentScanAnnotation = " + componentScanAnnotation);
            String scanPackage = componentScanAnnotation.value();//cn.xmp.spring
            String path = scanPackage.replace(".", "/");
            ClassLoader classLoader = this.getClass().getClassLoader();
            URL resource = classLoader.getResource(path);
            File fileResource = new File(resource.getFile());
            if (fileResource.isDirectory()) {
                File[] files = fileResource.listFiles();
                for (File file : files) {
//                    System.out.println("file1 = " + file);
                    //判断是否是属于bean
                    String absolutePath = file.getAbsolutePath();
                    String classPath = absolutePath.substring(absolutePath.indexOf("cn"), absolutePath.indexOf(".class")).replace("\\", ".");
                    Class<?> loadClass = classLoader.loadClass(classPath);
                    if (loadClass.isAnnotationPresent(Component.class)) {
                        String beanName = loadClass.getAnnotation(Component.class).value();
                        if (beanName.equals("")){
                            beanName = Introspector.decapitalize(loadClass.getSimpleName());
                        }
                        //将扫描到的bean放入元数据缓存池
                        Scope annotation = loadClass.getAnnotation(Scope.class);
                        BeanDefinition beanDefinition = new BeanDefinition();
                        beanDefinition.setType(loadClass);
                        beanDefinition.setScope(annotation == null ? "singleton" : annotation.value());
                        beanDefinitionConcurrentHashMap.put(beanName, beanDefinition);
                    }

                }
            }
            //创建单例bean，放入单例map缓存池
            for (String beanName : beanDefinitionConcurrentHashMap.keySet()) {
                BeanDefinition beanDefinition = beanDefinitionConcurrentHashMap.get(beanName);
                if ("singleton".equals(beanDefinition.getScope())){
                    createBean(beanName, beanDefinition);
                }
            }
        }
    }

    private Object createBean(String beanName, BeanDefinition beanDefinition) {
        Class aClass = beanDefinition.getType();
        Object o = null;
        try {
            o = aClass.getConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        singletonConcurrentHashMap.put(beanName, o);
        return o;
    }

    public Object getBean(String beanName) {
        BeanDefinition beanDefinition = beanDefinitionConcurrentHashMap.get(beanName);
        if ("singleton".equals(beanDefinition.getScope())) {
            Object o = singletonConcurrentHashMap.get(beanName);
            if (o == null) {
                Object bean = createBean(beanName, beanDefinition);
                singletonConcurrentHashMap.put(beanName, bean);
                return bean;
            }
            return o;
        } else {
            return createBean(beanName, beanDefinitionConcurrentHashMap.get(beanName));
        }
    }
}
