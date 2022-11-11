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
        String hej = "se.umu.cs.emli.MyUnitTester.test2";
        view.clearOutPut();
        try {
            //Also, TODO: Remove hardcoded value for classname. input should be here.
            ClassHolder holder = new ClassHolder(hej);
            TestWorker worker = new TestWorker(view,holder);
            worker.execute();
        } catch (ClassNotFoundException | NoClassDefFoundError e) {
            view.updateOutPut("No class with that name found.");
        } catch (NoSuchMethodException e) {
            view.updateOutPut("Invalid class. "+ hej
                    +" had no constructor or had constructor with parameters.");
        }
    }
}
