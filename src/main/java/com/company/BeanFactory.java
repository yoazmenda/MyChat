package com.company;


import com.company.annotations.Bean;
import com.company.annotations.Inject;
import com.company.utils.ClassFinder;
import com.company.annotations.Configuration;
import com.company.exp.NoSuchBeanException;
import com.company.annotations.Component;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public class BeanFactory {

    private static final Logger log = Logger.getLogger(BeanFactory.class.getName());

    private static int MAX_BEANS = 100;
    private static Map<Class<?>, Object> beans;
    private static String[] beanNames;
    private static int beanCount;
    private static Map<Class<?>, CustomConstructor> customBeans;


    public BeanFactory(String baseDir) {
        beans = new HashMap<>();
        customBeans = new HashMap<>();
        beanNames = new String[MAX_BEANS];
        beanCount = 0;

        init(baseDir);
    }

    public static void init(String baseDir) {
        log.log(Level.INFO, "Scanning for classes annotated by @Component");
        List<Class<?>> classes = ClassFinder.find(baseDir);

        searchForCustomBeans(baseDir);

        for (Class<?> clazz : classes) {

            // TODO: improve by having dependency tree
            // naive BFS approach of dependency management - no dependency graph calculation
            //if annotated with @Component AND not already created -> create it and it's children
            if (clazz.isAnnotationPresent(Component.class)) {

                createBeanRecursive(clazz);

            }
        }
    }

    private static void searchForCustomBeans(String baseDir) {
        List<Class<?>> classes = ClassFinder.find(baseDir);
        for (Class<?> clazz : classes) {
            if (clazz.isAnnotationPresent(Configuration.class)) {
                Method[] methods = clazz.getMethods();
                List<Method> customBeansMethods = Arrays.stream(methods).filter(method -> method.isAnnotationPresent(Bean.class)).collect(Collectors.toList());
                for (Method method : customBeansMethods){
                    Class<?> beanType = method.getReturnType();
                    try {
                        Object bean = method.invoke(clazz.newInstance(),method.getParameters());
                        beanNames[beanCount++] = clazz.getName();
                        beans.put(beanType, bean);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    }
                }

            }
        }



    }

    private static void createBeanRecursive(Class<?> clazz)  {
        if (beans.containsKey(clazz)){
            return;
        }
        Field[] fields = clazz.getDeclaredFields();
        List<Field> annotatedFields = Arrays.asList(fields).stream().filter(field -> field.isAnnotationPresent(Inject.class)).collect(Collectors.toList());
        boolean isLeaf = annotatedFields.isEmpty();
        Object bean;
        //create only if leaf and not already created
        if (isLeaf && !(beans.containsKey(clazz))) {
            bean = createBean(clazz);
        }


        /*
        now all the children have already been created in one of two ways:
            1. a child of this class was recognized and created
            2. another class had this child so IT created it and added it to the bean pool
         */


        // not a leaf - create all children and only then set them
        else {

            //create children
            for (Field field : annotatedFields) {
               createBeanRecursive(field.getType());
            }


            bean = createBean(clazz);

            //set children and create self
            for (Field field : annotatedFields) {
                field.setAccessible(true);
                Class<?> type = field.getType();
                Object rightSide = getBean(type);
                try {
                    field.set(bean,rightSide);
                } catch (IllegalAccessException e) {
                    log.log(Level.SEVERE, "Cannot set property: " + rightSide + " for bean: " + clazz.getName());
                    System.exit(-1);
                }
//                BeanUtils.setProperty(bean, field.getName(), getBean(field.getType()));
            }

        }
        beanNames[beanCount++] = clazz.getName();
        beans.put(clazz, bean);
    }

    private static Object createBean(Class<?> clazz) {
        Object bean = null;
        try {
            CustomConstructor customConstructor = customBeans.get(bean);

            // user empty constructor
//            if (customConstructor == null) {
            Constructor<?>[] ctors = clazz.getDeclaredConstructors();
            List<Constructor> emptyCtors = Arrays.asList(ctors).stream().filter(constructor -> constructor.getParameterCount()==0).collect(Collectors.toList());
            emptyCtors.forEach(constructor -> constructor.setAccessible(true));

            bean = clazz.newInstance();
//            }
            // use non-empty constructor def
//            else {
//                Constructor<?> constructor = new Constructor<?>(customConstructor.getClass(), );
//            }
        } catch (InstantiationException e) {
            log.log(Level.SEVERE, "Error while instantiating bean: " + clazz.getName());
            System.exit(-1);
        } catch (IllegalAccessException e) {
            log.log(Level.SEVERE, "Cannot access bean instantiation: " + clazz.getName());
            System.exit(-1);
        }
        return bean;
    }

    public static Object getBean(Class<?> clazz) {
        Object bean = beans.get(clazz);
        if (bean == null) {
            throw new NoSuchBeanException(clazz);
        }
        return bean;
    }

    public void run(Class<?> classToTun, String[] args) {
        Method main = null;
        try{
            main = classToTun.getMethod("main", String[].class);
        }
        catch (NoSuchMethodException e){
            log.log(Level.SEVERE, "Can't find main method in class: " + classToTun.getName());
            System.exit(-1);
        }
        try {
            main.invoke(args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        finally {
            System.exit(0);
        }
    }

    public String[] getBeanDefinitionNames() {
        return beanNames;
    }

    public int getBeanCount() {
        return beanCount;
    }

    private static class CustomConstructor {
        private Parameter[] params;
        private Class<?> returnType;
        private Method method;

        public CustomConstructor(Parameter[] params, Class<?> returnType, Method method) {
            this.params = params;
            this.returnType = returnType;
            this.method = method;
        }

        public CustomConstructor() {
        }

        public Parameter[] getParams() {
            return params;
        }

        public void setParams(Parameter[] params) {
            this.params = params;
        }

        public Class<?> getReturnType() {
            return returnType;
        }

        public void setReturnType(Class<?> returnType) {
            this.returnType = returnType;
        }

        public Method getMethod() {
            return method;
        }

        public void setMethod(Method method) {
            this.method = method;
        }
    }
}
