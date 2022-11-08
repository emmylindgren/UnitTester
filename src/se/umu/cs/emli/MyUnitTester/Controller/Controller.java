package se.umu.cs.emli.MyUnitTester.Controller;

import se.umu.cs.emli.MyUnitTester.View.UnitTestView;

public class Controller {
    private UnitTestView view;

    /**
     * Init the controller functionality and graphical user interface.
     */
    public Controller(){
        this.view = new UnitTestView("Unit tester");
        this.view.setClearOutPutListener(e -> view.clearOutPut());

        view.show();
    }

    /*
        ClassHolder holder = new ClassHolder("se.umu.cs.emli.MyUnitTester.Test1");
        boolean isValid = holder.isClassValid();
        System.out.println(isValid);
        holder.loadMethods();*/
}
