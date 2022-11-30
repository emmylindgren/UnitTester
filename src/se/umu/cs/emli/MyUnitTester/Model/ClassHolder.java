package se.umu.cs.emli.MyUnitTester.Model;

import se.umu.cs.unittest.TestClass;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

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
     * Gets methods from class and sorts out test-methods.
     * A test-method starts with the name tests, does not take any parameters and
     * returns a boolean value.
     * If setUp and tearDown exist then they are also saved in the model,
     * if not they are saved as null.
     *
     * @return a list with test-method names from the class.
     */
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
        List<String> testMethods = new ArrayList<>();
        for (Method method: methods) {
            if(method.getName().startsWith("test") &&
                    method.getReturnType().getName().equals("boolean") &&
                    method.getParameterCount() == 0){
                testMethods.add(method.getName());
            }
        }
        return testMethods;
    }

    /**
     * Method to invoke method setUp or tearDown.
     * Needed as these method does not necessarily return boolean values
     * and does not need to exist in class.
     * @param choice, a string containing the choice of setUp or tearDown.
     * @throws InvocationTargetException, if the method could not be invoked.
     * @throws IllegalAccessException, if the method could not be accessed.
     */
    public void invokeSetUpTearDown(String choice) throws InvocationTargetException, IllegalAccessException {
        if(choice.equals("setUp") && setUp != null){
           setUp.invoke(o);
        }
        else if(choice.equals("tearDown") && tearDown !=null){
            tearDown.invoke(o);
        }
    }

    /**
     * Method for invoking methods from method-names.
     * @param methodName, the name of the method to be invoked.
     * @return boolean value, the result of the method invoked.
     * @throws NoSuchMethodException, if the method does not exist.
     * @throws InvocationTargetException, if the method could not be invoked.
     * @throws IllegalAccessException, if the method could not be accessed.
     */
    public boolean invokeMethod(String methodName) throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {
        return (boolean) c.getMethod(methodName).invoke(o);
    }

}
