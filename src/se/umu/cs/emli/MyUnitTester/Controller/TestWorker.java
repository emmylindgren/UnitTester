package se.umu.cs.emli.MyUnitTester.Controller;

import se.umu.cs.emli.MyUnitTester.Model.ClassHolder;
import se.umu.cs.emli.MyUnitTester.View.UnitTestView;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * A Swing-worker class to run test-methods.
 * Runs test-methods on given class and prints the results after every test.
 * @author Emmy Lindgren, id19eln
 */

public class TestWorker extends SwingWorker<String,String>{
    private final ClassHolder classHolder;
    private final UnitTestView view;

    public TestWorker (UnitTestView view, ClassHolder classHolder){
        this.classHolder = classHolder;
        this.view = view;
    }

    /**
     * Executes possibly time-consuming method-invocations.
     * Runs on different thread than EDT and should NOT manipulate the UI in any way.
     * To manipulate UI with partial results from testMethods, publish() is used to send
     * results to @process, which runs on EDT.
     * @return string with final results of the testruns.
     */
    @Override
    protected String doInBackground() {
        if (classHolder.isValid()) {
            while (classHolder.hasTestMethodsToRun()) {
                publish(classHolder.runNextTestMethod());
            }
            return classHolder.getResults();
        }else{
            return ("Invalid class. " + classHolder.getInvalidReason());
        }
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
     * Prints the results of the testsMethods.
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
}