package se.umu.cs.emli.MyUnitTester.Controller;

import se.umu.cs.emli.MyUnitTester.Model.ClassHolder;
import se.umu.cs.emli.MyUnitTester.Model.ResultHolder;
import se.umu.cs.emli.MyUnitTester.View.UnitTestView;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * A Swing-worker class to run test-methods.
 * Runs test-methods on given class and prints the results after every test.
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

    /**
     * Executes possibly time-consuming method-invocations.
     * Runs on different thread than EDT and should NOT manipulate the UI in any way.
     * To manipulate UI with partial results from methods, publish() is used to send
     * results to @process, which runs on EDT.
     * Disables runbutton in view to keep user from running more test when a test is in progress.
     * @return string with final results of the testruns.
     */
    @Override
    protected String doInBackground(){

        view.disableRunButton();
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
            return ("Invalid class. " + classHolder.getInvalidReason());
        }
        return resultHolder.getResultText();
    }

    /**
     * Processing data from @doInBackground() while the loop is running. Gets data from @doInBackground with
     * the help of method Publish(chunk). Gets put in list so no chunk is missed if several calls to publish is made
     * before the first has processed.
     * Runs on the EDT and is therefor safe to manipulate the UI.
     * @param chunks intermediate results to process. A list containing strings with output.
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
     * Prints the results of the tests.
     * Enables runbutton in view to let the user run other tests now
     * that these tests are done.
     */
    @Override
    public void done(){
        view.enableRunButton();
        try {
            String resultText = get();
            view.updateOutPut(resultText);
        } catch (ExecutionException | InterruptedException e) {
            view.updateOutPut("Error occurred: " + e.getCause());
        }
    }

    /**
     * Method to invoke setup and teardown methods in class.
     * Needed to be able to print the right error-messages to user and because
     * setup and teardown does not return boolean.
     * @param choice, the choice of setUp or tearDown method to be run.
     */
    private void invokeSetUpTearDown(String choice){
        try {
            classHolder.invokeSetUpTearDown(choice);
        } catch (InvocationTargetException e) {
            publish("Method: " + choice + "can not be instantiated.");
        } catch (IllegalAccessException e) {
            publish("Class does not give permission to run method: " + choice);
        }
    }
}