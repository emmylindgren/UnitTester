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
    private List<String> testMethods;
    private String className;
    private String invalidReason;

    public ClassHolder(String className){
        this.className = className;
        //TODO: Handle exceptions here. Should be handled in controller! (not worker)
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
    public boolean isValid(){

        if(con.getParameterCount() !=0){
            invalidReason = className + "have too many parameters.";
            return false;
        }

        //TODO: Handle the exceptions here: Write to UI, but do that from CONTROLLER! Swingworker?
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

    //TODO: Return list of methods here?
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
        testMethods = new ArrayList<String>();
        for (Method method: methods) {
            if(method.getName().startsWith("test") &&
                    method.getReturnType().getName().equals("boolean")){
                testMethods.add(method.getName());
            }
        }
        //TODO: Remove prints.
        System.out.println("Metoder");
        System.out.println(testMethods);
        return testMethods;
    }

    public void invokeSetUpTearDown(String choice){
        if((choice.equals("setUp") && setUp != null)
            || (choice.equals("tearDown") && tearDown !=null)){

            try {
                c.getMethod(choice).invoke(o);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public boolean invokeMethod(String methodName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return (boolean) c.getMethod(methodName).invoke(o);
    }
}
