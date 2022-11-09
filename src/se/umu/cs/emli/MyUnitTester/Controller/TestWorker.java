package se.umu.cs.emli.MyUnitTester.Controller;

import se.umu.cs.emli.MyUnitTester.Model.ClassHolder;
import se.umu.cs.emli.MyUnitTester.Model.ResultHolder;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Swingworker class to run testmethods.
 */

public class TestWorker extends SwingWorker {

    private ClassHolder classHolder;
    private JTextArea textArea;
    private ResultHolder resultHolder;

    public TestWorker (JTextArea textArea, ClassHolder classHolder){
        this.classHolder = classHolder;
        this.textArea = textArea;
        this.resultHolder = new ResultHolder();
    }


        /* Old code but i think it will help.
    ClassHolder holder = new ClassHolder("se.umu.cs.emli.MyUnitTester.Test1");
    boolean isValid = holder.isClassValid();
    System.out.println(isValid);
    holder.loadMethods();: gör om denna så den hämtar alla metoder? Sortera här ist? Nja?  */
    @Override
    protected ResultHolder doInBackground() {

        if(classHolder.isValid()){
            List<String> testMethods = classHolder.getTestMethodNames();

            for (String method:testMethods) {
                try {
                    boolean result = classHolder.invokeMethod(method);

                    if(result){
                        //printa method (asså namnet) + : SUCCESS
                        resultHolder.addSuccessTest();
                    }
                    else{
                        //printa method + : FAIL
                        resultHolder.addFailedTest("tom sträng ifall den bara faila");
                    }

                    //om den fick exception så lägg till i failed test med exceptionnamnet! och
                    //skriv ut även här.
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }

            }

        }
        else{
            //TODO: hur ska jag stoppa detta? Kasta undantag? Returnera null?
            String invalid = "Invalid test class." + classHolder.getInvalidReason();
        }


        return null;
    }


    //Runs when doinbackground is done. Runs on the EDT and not on the swingworker?
    protected void done(){
        //Here we should print the final results

    }

    //Use this to publish during the doinbackground? Publish results from
    //every iteration.
    @Override
    protected void process(List chunks) {
        //Here we should print result from every iteration?
        super.process(chunks);
    }
}
