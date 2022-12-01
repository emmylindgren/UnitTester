package se.umu.cs.emli.MyUnitTester.Model;

import se.umu.cs.unittest.TestClass;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Stack;

/**
 * Class to hold info about test-classes.
 * @author Emmy Lindgren, id19eln
 */
public class ClassHolder {
    private final Class<?> c;
    private final Constructor<?> con;
    private Object o;
    private Method setUp;
    private Method tearDown;
    private final String className;
    private String invalidReason;
    private Stack<Method> testMethods;
    private final ResultHolder resultHolder;

    public ClassHolder(String className) throws NoSuchMethodException, NoClassDefFoundError, ClassNotFoundException {
        Class<?> c1;
        this.className = className;

        ClassLoader loader = this.getClass().getClassLoader();
        try {
            c1 = loader.loadClass(className);
        } catch (ClassNotFoundException e) {
            c1 = Class.forName("se.umu.cs.emli.MyUnitTester."+className);
        }
        c = c1;
        con = c.getConstructor();
        getTestMethods();
        resultHolder = new ResultHolder();
    }

    /**
     * Checks if the current class is a valid test-class.
     * i.e. implements the interface @TestClass and does not have a private constructor.
     * If it's not a valid test-class then reason for not being valid is saved in invalidReason.
     *
     * @return boolean true if class is valid, false if not.
     */
    public boolean isValid(){
        try {
            o = con.newInstance();
        } catch (InstantiationException | InvocationTargetException e) {
            invalidReason = className + " can not be instantiated.";
            return false;
        } catch (IllegalAccessException e) {
            invalidReason = className + " does not give permission to constructor.";
            return false;
        }

        if(!(o instanceof TestClass)){
            invalidReason = className + " does not implement TestClass-interface.";
            return false;
        }
        return true;
    }

    public String getInvalidReason(){return invalidReason;}

    /**
     * Gets methods from class and sorts out test-methods. Saves them in list of methods to run.
     * A test-method starts with the name tests, does not take any parameters and
     * returns a boolean value.
     * If setUp and tearDown exist then they are also saved in the model,
     * if not they are saved as null.
     */
    private void getTestMethods(){
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
        testMethods = new Stack<>();
        for (Method method: methods) {
            if(method.getName().startsWith("test") &&
                    method.getReturnType().getName().equals("boolean") &&
                    method.getParameterCount() == 0){
                testMethods.push(method);
            }
        }
    }

    /**
     * Method to invoke method setUp or tearDown.
     * Needed as these method does not necessarily return boolean values
     * and does not need to exist in class.
     * @param choice, a string containing the choice of setUp or tearDown.
     */
    private String invokeSetUpTearDown(String choice){
        try {
            if(choice.equals("setUp") && setUp != null){
                setUp.invoke(o);
            }
            else if(choice.equals("tearDown") && tearDown !=null){
                tearDown.invoke(o);
            }
            return "";
        } catch (IllegalAccessException e) {
            return "Method "+choice+ "could not be accessed.";
        } catch (InvocationTargetException e) {
            return "Class does not give permission to run method: " + choice;
        }
    }

    /**
     * Checks if there are any testmethods to run.
     * @return boolean, saying if there are any testmethods to run.
     */
    public boolean hasTestMethodsToRun(){
        return !testMethods.isEmpty();
    }

    /**
     * Method to run test method next in line from testmethod-stack.
     * Invokes setUp then runs testmethod. The result is added to resultholder and
     * tearDown is invoked. Then the result is returned in form of a string.
     * @return the result, in form of a string.
     */
    public String runNextTestMethod() {
        Method method = testMethods.pop();
        String result = "";
        try {
            result += invokeSetUpTearDown("setUp");
            boolean testPassed = (boolean) method.invoke(o);

            if (testPassed) {
                resultHolder.addSuccessTest();
                result += method.getName() + ": SUCCESS";
            } else {
                resultHolder.addFailedTest();
                result += method.getName() + ": FAIL";
            }
            result += invokeSetUpTearDown("tearDown");
        } catch (InvocationTargetException e) {
            resultHolder.addException();
            result += method.getName() + ": FAIL Generated a " + e.getCause().getClass().getName();
        } catch (IllegalAccessException e) {
           result += "Class does not give permission to run method: " + method.getName();
        }
        return result;
    }

    /**
     * Returns the final string of all results.
     * @return string with final results.
     */
    public String getResults(){
        return resultHolder.getResultText();
    }
}
