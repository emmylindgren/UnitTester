package se.umu.cs.emli.MyUnitTester.Controller;

import se.umu.cs.emli.MyUnitTester.Model.ClassHolder;
import se.umu.cs.emli.MyUnitTester.View.UnitTestView;

public class Controller {
    private final UnitTestView view;

    /**
     * Init the controller. Sets up the graphical user interface, and it's listeners.
     */
    public Controller(){
        this.view = new UnitTestView("Unit tester");
        setUpListeners();

        view.show();
    }

    private void setUpListeners(){
        this.view.setSendInputListener(e -> this.handleInput());
        this.view.setClearOutPutListener(e -> view.clearOutPut());
    }

    /**
     * Handles the input from users, AKA collects the input from UI and
     * creates a class of the test class with name from the input.
     * Then starts a swing-worker to run the test-methods from the class.
     */
    private void handleInput(){
        String input = this.view.getInputValue();
        view.clearOutPut();
        try {
            //Also, TODO: Remove hardcoded value for classname. input should be here.
            ClassHolder holder = new ClassHolder("se.umu.cs.emli.MyUnitTester.Test1");
            TestWorker worker = new TestWorker(view,holder);
            worker.execute();
        } catch (ClassNotFoundException e) {
            view.updateOutPut("No class with that name found.");
        } catch (NoSuchMethodException e) {
            view.updateOutPut("Test class had no constructor.");
        }
    }
}
