package se.umu.cs.emli.MyUnitTester.Controller;

import se.umu.cs.emli.MyUnitTester.Model.ClassHolder;
import se.umu.cs.emli.MyUnitTester.Model.ResultHolder;
import se.umu.cs.emli.MyUnitTester.View.UnitTestView;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Swing-worker class to run test-methods.
 */

public class TestWorker extends SwingWorker<String,String>{

    private final ClassHolder classHolder;
    private final UnitTestView view;
    private final ResultHolder resultHolder;

    public TestWorker (UnitTestView view, ClassHolder classHolder){
        this.classHolder = classHolder;
        this.view = view;
        this.resultHolder = new ResultHolder();
    }

    @Override
    protected String doInBackground(){

        if(classHolder.isValid()){
            List<String> testMethods = classHolder.getTestMethodNames();

            for (String method:testMethods) {
                try {
                    invokeSetUpTearDown("setUp");
                    boolean result = classHolder.invokeMethod(method);

                    if(result){
                        publish(method + ": SUCCESS");
                        resultHolder.addSuccessTest();
                    }
                    else{
                        publish(method + ": FAIL");
                        resultHolder.addFailedTest();
                    }
                } catch (NoSuchMethodException e) {
                    publish(method + ": does not exist.");
                } catch (InvocationTargetException e) {
                    publish(method + ": FAIL Generated a "+ e.getCause().getClass().getName());
                    resultHolder.addException();
                } catch (IllegalAccessException e) {
                    publish("Class does not give permission to run method: "+method);
                }
                finally {
                    invokeSetUpTearDown("tearDown");
                }
            }
        }
        else{
            return ("Invalid class." + classHolder.getInvalidReason());
        }
        return resultHolder.getResultText();
    }

    private void invokeSetUpTearDown(String choice){
        try {
            classHolder.invokeSetUpTearDown(choice);
        } catch (InvocationTargetException e) {
            publish("Method: " + choice + "can not be instantiated.");
        } catch (IllegalAccessException e) {
            publish("Class does not give permission to run method: " + choice);
        }
    }

    /**
     * Processing data from @doInBackground() while the loop is running.
     * Runs on the EDT and is therefor safe to manipulate the UI.
     * @param chunks intermediate results to process. A list containing
     *
     */
    @Override
    protected void process(List chunks) {
        for (Object text: chunks) {
            view.updateOutPut((text.toString() + System.lineSeparator()));
        }
    }

    /**
     * Runs on the EDT after @doInBackground has finished.
     */
    @Override
    public void done(){
        try {
            String outPutText = get();
            view.updateOutPut(outPutText);
        } catch (ExecutionException | InterruptedException e) {
            view.updateOutPut("Error occurred: " + e.getCause());
        }
    }

}
