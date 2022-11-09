package se.umu.cs.emli.MyUnitTester.Model;

import se.umu.cs.unittest.TestClass;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


//TODO: Fix exceptions in this class.
public class ClassHolder {
    private Class<?> c;
    private Constructor<?> con;
    private Object o;
    private Method setUp;
    private Method tearDown;
    private List<String> testMethods;
    private String className;
    private String invalidReason;

    public ClassHolder(String className) throws ClassNotFoundException, NoSuchMethodException {
        this.className = className;
        c = Class.forName(className);
        con = c.getConstructor();
    }

    /**
     * Checks if the current class is a valid testclass.
     * i.e. does not take any parameters and implements the
     * interface @TestClass.
     *
     * @return boolean true if class is valid, false if not.
     */
    public boolean isValid(){

        if(con.getParameterCount() !=0){
            invalidReason = className + "have too many parameters.";
            return false;
        }

        //TODO: Handle the exceptions here: Write to UI, but do that from CONTROLLER! Swingworker? Return false och sätt
        // om reason till nåt annat eventuellt?
        try {
            o = con.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        if(!(o instanceof TestClass)){
            invalidReason = className + "does not implement TestClass-interface.";
            return false;
        }

        return true;
    }

    public String getInvalidReason(){return invalidReason;}


    public List<String> getTestMethodNames(){
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
                testMethods.add(method.getName());
            }
        }
        return testMethods;
    }

    public void invokeSetUp(){
        if(setUp != null) {
            try {
                setUp.invoke(o);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void invokeTearDown(){
        if(tearDown != null) {
            try {
                tearDown.invoke(o);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean invokeMethod(String methodName) throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {
        return (boolean) c.getMethod(methodName).invoke(o);
    }

}
