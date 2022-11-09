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
        this.view.setSendInputListener(e -> this.handleInput());
        this.view.setClearOutPutListener(e -> view.clearOutPut());
    }

    private void handleInput(){
        String text = this.view.getInputValue();
        view.clearOutPut();

        ClassHolder holder = new ClassHolder("se.umu.cs.emli.MyUnitTester.Test1");
        TestWorker worker = new TestWorker(view,holder);
        worker.execute();

        //Also, TODO: Remove print!
        System.out.println(text);
    }


}
