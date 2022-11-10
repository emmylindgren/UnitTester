package se.umu.cs.emli.MyUnitTester.Controller;

import se.umu.cs.emli.MyUnitTester.Model.ClassHolder;
import se.umu.cs.emli.MyUnitTester.View.UnitTestView;

public class Controller {
    private UnitTestView view;

    /**
     * Init the controller. Setting up the graphical user interface,
     * and it's listeners.
     */
    public Controller(){
        this.view = new UnitTestView("Unit tester");
        setUpListeners();

        view.show();
    }

    private void setUpListeners(){
        //TODO: Set up listener on textfield for enter press: also send input!
        this.view.setSendInputListener(e -> this.handleInput());
        this.view.setClearOutPutListener(e -> view.clearOutPut());
    }

    private void handleInput(){
        String input = this.view.getInputValue();
        view.clearOutPut();
        try {
            //Also, TODO: Remove hardcoded value for classname. input should be here.
            ClassHolder holder = new ClassHolder("se.umu.cs.emli.MyUnitTester.Test1");
            TestWorker worker = new TestWorker(view,holder);
            worker.execute();
        } catch (ClassNotFoundException e) {
            view.updateOutPut("No testclass with that name found.");
        } catch (NoSuchMethodException e) {
            view.updateOutPut("Testclass had no constructor.");
        }
    }
}
