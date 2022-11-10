package se.umu.cs.emli.MyUnitTester.Controller;

import se.umu.cs.emli.MyUnitTester.Model.ClassHolder;
import se.umu.cs.emli.MyUnitTester.Model.ResultHolder;
import se.umu.cs.emli.MyUnitTester.View.UnitTestView;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Swingworker class to run testmethods.
 */

//TODO: Maybe it should take parameters of some sort?
public class TestWorker extends SwingWorker<String,String>{

    private ClassHolder classHolder;
    private UnitTestView view;
    private ResultHolder resultHolder;

    public TestWorker (UnitTestView view, ClassHolder classHolder){
        this.classHolder = classHolder;
        this.view = view;
        this.resultHolder = new ResultHolder();
    }

    @Override
    protected String doInBackground() throws InterruptedException {

        if(classHolder.isValid()){
            List<String> testMethods = classHolder.getTestMethodNames();

            for (String method:testMethods) {
                try {
                    classHolder.invokeSetUp();
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
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    publish(method + ": FAIL Generated a "+ e.getCause().getClass().getName());
                    resultHolder.addException();
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                finally {
                    classHolder.invokeTearDown();
                }

            }
        }
        else{
            return ("Invalid class." + classHolder.getInvalidReason());
        }
        return resultHolder.getResultText();
    }

    /**
     * Processing data from @doInBackground() while the loop is running.
     * Runs on the EDT and is therefor safe to manipulate the UI.
     * @param chunks intermediate results to process
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
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
