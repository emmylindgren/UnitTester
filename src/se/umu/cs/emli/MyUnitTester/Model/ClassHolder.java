package se.umu.cs.emli.MyUnitTester.Model;

import se.umu.cs.unittest.TestClass;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ClassHolder {
    private Class<?> c;
    private Constructor<?> con;
    private Object o;
    private Method setUp;
    private Method tearDown;
    private List<Method> testMethods;

    public ClassHolder(String className){
        try {
            c = Class.forName(className);
            con = c.getConstructor();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Checks if the current class is a valid testclass.
     * i.e. does not take any parameters and implements the
     * interface @TestClass.
     *
     * @return boolean true if class is valid, false if not.
     */
    public boolean isClassValid(){
        if(con.getParameterCount() !=0) return false;

        //TODO: Handle the exceptions here?
        try {
            o = con.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        return o instanceof TestClass && con.getParameterCount() == 0;
    }

    //TODO: Return list of methods here?
    public void loadMethods(){
        try {
            setUp = c.getMethod("setUp");
        } catch (NoSuchMethodException e) {
            setUp = null;
        }
        try {
            tearDown = c.getMethod("tearDown");
        } catch (NoSuchMethodException e) {
            tearDown = null;
        }

        Method[] methods = c.getMethods();
        testMethods = new ArrayList<>();
        for (Method method: methods) {
            if(method.getName().startsWith("test") &&
                    method.getReturnType().getName().equals("boolean")){
                testMethods.add(method);
            }
        }
        //TODO: Remove prints.
        System.out.println("Metoder");
        System.out.println(testMethods);
    }
}
